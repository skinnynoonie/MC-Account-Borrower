package me.skinnynoonie.mcab.data;

import net.dv8tion.jda.api.interactions.InteractionHook;

public final class BeamSession {

    private String email;
    private String username;
    private String code;
    private InteractionHook verifyingCodeHook;

    public String getEmail() {
        return this.email;
    }

    public BeamSession setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public BeamSession setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public BeamSession setCode(String code) {
        this.code = code;
        return this;
    }

    public InteractionHook getVerifyingCodeHook() {
        return this.verifyingCodeHook;
    }

    public BeamSession setVerifyingCodeMsgHook(InteractionHook verifyingCodeHook) {
        this.verifyingCodeHook = verifyingCodeHook;
        return this;
    }

}
