package com.example.insurancemanagementbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Minimal Supabase Storage client using REST endpoints.
 * NOTE: Use service role key ONLY on the server. Do not expose it to the frontend.
 * This class provides a method to create signed URLs for private objects.
 */
@Component
public class SupabaseStorageClient {

    @Value("${SUPABASE_URL:}")
    private String supabaseUrl;

    @Value("${SUPABASE_SERVICE_ROLE_KEY:}")
    private String serviceRoleKey;

    @Value("${SUPABASE_BUCKET_CLAIMS:claims-attachments}")
    private String claimsBucket;

    private final RestTemplate restTemplate = new RestTemplate();

    public String createSignedUrl(String objectPath, int expiresInSeconds) {
        if (supabaseUrl == null || supabaseUrl.isBlank() || serviceRoleKey == null || serviceRoleKey.isBlank()) {
            throw new IllegalStateException("Supabase URL or service role key is not configured");
        }
        String url = String.format("%s/storage/v1/object/sign/%s/%s?download=false",
                supabaseUrl.replaceAll("/+$", ""), claimsBucket, objectPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", serviceRoleKey);
        headers.set("Authorization", "Bearer " + serviceRoleKey);

        String body = String.format("{\"expiresIn\": %d}", expiresInSeconds);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create signed URL: " + response.getStatusCode());
        }
        return response.getBody();
    }

    public String getClaimsBucket() {
        return claimsBucket;
    }
}
