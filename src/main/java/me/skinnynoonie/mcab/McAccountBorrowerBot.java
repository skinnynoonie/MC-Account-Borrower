package me.skinnynoonie.mcab;

import me.skinnynoonie.mcab.listener.ButtonListener;
import me.skinnynoonie.mcab.listener.ModalListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class McAccountBorrowerBot extends ListenerAdapter {

    public static void main(String[] args) {
        new McAccountBorrowerBot(args[0], args[1]);
    }

    private final JDA jda;
    private final ExecutorService executorService;

    public McAccountBorrowerBot(String token, String logChannelId) {
        this.jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();

        this.jda.addEventListener(
                this,
                new ButtonListener(),
                new ModalListener(this.jda, this.executorService, logChannelId)
        );
        this.jda.upsertCommand(Messages.INITIAL_PROMPT_SEND_CMD).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("send-message")) {
            return;
        }

        event.deferReply().setEphemeral(true).queue();

        this.executorService.submit(() -> {
            InteractionHook hook = event.getHook();
            try {
                event.getChannel().sendMessageEmbeds(Messages.INITIAL_PROMPT_EMBED)
                        .addActionRow(Messages.INITIAL_PROMPT_REGISTER_BUTTON)
                        .complete();
                hook.editOriginal("Successfully sent initial prompt message.").complete();
            } catch (Exception e) {
                e.printStackTrace();
                hook.editOriginal("Failed to send initial prompt message.").complete();
            }
        });
    }

}
