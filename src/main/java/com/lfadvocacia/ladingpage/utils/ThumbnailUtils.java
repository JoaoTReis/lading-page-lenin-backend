package com.lfadvocacia.ladingpage.utils;

import java.util.regex.Pattern;

public class ThumbnailUtils {

    public static String normalizeThumbnail(String url) {
        if (url == null || url.isEmpty()) return url;

        // --- GOOGLE DRIVE ---
        if (url.contains("drive.google.com")) {
            var match = url.split("/d/");
            if (match.length > 1) {
                var id = match[1].split("/")[0];
                return "https://drive.google.com/uc?export=view&id=" + id;
            }
            return url;
        }

        // --- IMGUR ---
        if (url.contains("imgur.com")) {
            String regex = "imgur\\.com/(?:gallery/|a/)?([a-zA-Z0-9]+)";
            var matcher = Pattern.compile(regex).matcher(url);

            if (matcher.find()) {
                String id = matcher.group(1);
                return "https://i.imgur.com/" + id + ".jpg";
            }

            return url;
        }

        return url;
    }
}
