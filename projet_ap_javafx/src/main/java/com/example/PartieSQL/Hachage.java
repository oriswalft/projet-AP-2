package com.example.PartieSQL;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hachage {

    // Contiens les méthodes nécessaires au hachage du mot de passe. Accès statique pour ne pas avoir le besoin d'instancier un objet de hachage.
    
    protected static String hashPassword(String pw) throws NoSuchAlgorithmException {
        String res = "";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(pw.getBytes(StandardCharsets.UTF_8));

        res = convertToHex(messageDigest);
        return res;
    }

    private static String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);

        while (hexText.length() < 32) {
            // Ajouter autant de 0 qu'il manque au début de la chaîne de caractère, afin d'éviter tout problème
            hexText = "0".concat(hexText);
        }
        return hexText;
    }
}
