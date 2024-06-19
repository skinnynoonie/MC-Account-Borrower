package me.skinnynoonie.mcab.listener;

import me.skinnynoonie.mcab.GlobalMetadataCache;
import me.skinnynoonie.mcab.Messages;
import me.skinnynoonie.mcab.data.BeamSession;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class ButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();

        if (Messages.INITIAL_PROMPT_REGISTER_BUTTON.getId().equals(buttonId)) {
            event.replyModal(Messages.REGISTER_FORM_MODAL).queue();
        }

        if (Messages.VERIFY_CODE_BUTTON.getId().equals(buttonId)) {
            event.replyModal(Messages.VERIFY_CODE_MODAL).queue();
        }

        if (Messages.SEND_INVALID_CODE_ERROR_BUTTON.getId().equals(buttonId)) {
            BeamSession beamSession = (BeamSession) GlobalMetadataCache.getMetadata(event.getMessageId());
            beamSession.getVerifyingCodeHook().editOriginalEmbeds(Messages.INVALID_CODE_ERROR_EMBED).complete();
            event.reply("Successfully sent error message.").setEphemeral(true).complete();
            event.getMessage().editMessageComponents().queue();
        }
    }

}
