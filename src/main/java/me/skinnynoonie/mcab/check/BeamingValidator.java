package me.skinnynoonie.mcab.check;

import java.util.regex.Pattern;

public final class BeamingValidator {

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.compile(ePattern).matcher(email).matches();
    }

    public static boolean isValidCode(String code) {
        String cPattern = "^\\d{7}$";
        return Pattern.compile(cPattern).matcher(code).matches();
    }

    public static boolean isValidUsername(String username) {
        String uPattern = "^[a-zA-Z0-9_]{2,16}$";
        return Pattern.compile(uPattern).matcher(username).matches();
    }

    private BeamingValidator() {
    }

}
