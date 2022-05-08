package chat.client.util;

import java.util.Formatter;

public class SystemMessages {

    public static void printLogo() {
        System.out.println(Constants.LOGO_COLOR +
                "\n,-_/               ,--. .       .  \n" +
                "'  | ,-. .  , ,-. | `-' |-. ,-. |- \n" +
                "   | ,-| | /  ,-| |   . | | ,-| |  \n" +
                "   | `-^ `'   `-^ `--'  ' ' `-^ `' \n" +
                "/` |                               \n" +
                "`--'\n"
                + Constants.COLOR_RESET
        );
    }

    public static void printHelpInfo() {
        System.out.println(Constants.SYSTEM_INFO_COLOR
                + "\nType "
                + Constants.COLOR_RESET
                + "help: "
                + Constants.SYSTEM_INFO_COLOR
                + "to see available commands or simply start chatting with other people\n"
                + Constants.COLOR_RESET);
    }

    public static void printInstruction() {
        System.out.println(Constants.SYSTEM_MESSAGE_COLOR
                + "### Enter your username for a group chat ###\n"
                + Constants.COLOR_RESET);
    }

    public static void printWelcomeMessage() {
        System.out.println(Constants.SYSTEM_MESSAGE_COLOR
                + "### Welcome to channel: "
                + Constants.COLOR_RESET
                + "Main "
                + Constants.SYSTEM_MESSAGE_COLOR
                + "###\n"
                + Constants.COLOR_RESET);
    }

    public static void printHistoryChannelAvailable(String channelName) {
        Formatter formatter = new Formatter();
        System.out.println(formatter.format("%s### History of the channel %s%s%s: ###%s%n"
                , Constants.SYSTEM_MESSAGE_COLOR
                , Constants.COLOR_RESET
                , channelName
                , Constants.SYSTEM_MESSAGE_COLOR
                , Constants.COLOR_RESET));
        formatter.close();
    }

    public static void printHistoryChannelUnavailable(String channelName) {
        Formatter formatter = new Formatter();
        System.out.println(formatter.format("%n%s### History of the channel %s%s%s is not available ###%s%n"
                , Constants.SYSTEM_MESSAGE_COLOR
                , Constants.COLOR_RESET
                , channelName.substring(0, 1).toUpperCase() + channelName.substring(1).toLowerCase()
                , Constants.SYSTEM_MESSAGE_COLOR
                , Constants.COLOR_RESET));
        formatter.close();
    }

    public static void printChannelsAvailable() {
        Formatter formatter = new Formatter();
        System.out.println(formatter.format("%s### Your channels: ###%s"
                , Constants.SYSTEM_MESSAGE_COLOR
                , Constants.COLOR_RESET));
        formatter.close();
    }

    public static void printCommandsAvailableInChat() {
        try (Formatter formatter = new Formatter()) {
            System.out.println((formatter.format("%s### Available commands: ###%s%n" +
                            "- channel:{channel name} - %sswitch to a new channel%s%n" +
                            "- showmychannels: - %scheck channels you have visited%s%n" +
                            "- history:{channel name} - %scheck the history of one of the channels you have visited%s%n" +
                            "- uploadfile:{file path} - %ssend a file to the server and notifies other members of your current channel%s%n" +
                            "- download:{file path} - %sdownloads a file from a server sent by another user%s%n" +
                            "- help: - %scheck available commands%s%n" +
                            "- exit: - %squit the chat%s%n"
                    , Constants.SYSTEM_MESSAGE_COLOR
                    , Constants.COLOR_RESET
                    , Constants.SYSTEM_MESSAGE_COLOR
                    , Constants.COLOR_RESET
                    , Constants.SYSTEM_MESSAGE_COLOR
                    , Constants.COLOR_RESET
                    , Constants.SYSTEM_MESSAGE_COLOR
                    , Constants.COLOR_RESET
                    , Constants.SYSTEM_MESSAGE_COLOR
                    , Constants.COLOR_RESET
                    , Constants.SYSTEM_MESSAGE_COLOR
                    , Constants.COLOR_RESET
                    , Constants.SYSTEM_MESSAGE_COLOR
                    , Constants.COLOR_RESET
                    , Constants.SYSTEM_MESSAGE_COLOR
                    , Constants.COLOR_RESET)));
        }
    }

    public static void goodbyeMessage() {
        Formatter formatter = new Formatter();
        System.out.println(formatter.format("%s### See you next time ###%s"
                , Constants.SYSTEM_CRITICAL_MESSAGE_COLOR
                , Constants.COLOR_RESET));
        formatter.close();
    }

    public static void printNewChannelWelcomeMessage(String channelName) {
        System.out.println(Constants.SYSTEM_MESSAGE_COLOR
                + "### Welcome to channel: "
                + Constants.COLOR_RESET
                + channelName.substring(0, 1).toUpperCase() + channelName.substring(1).toLowerCase()
                + Constants.SYSTEM_MESSAGE_COLOR
                + " ###\n"
                + Constants.COLOR_RESET);
    }

    public static void printTakenUsernameMessage(String clienName) {
        System.out.println(Constants.SYSTEM_CRITICAL_MESSAGE_COLOR
                + "\n### Username "
                + Constants.COLOR_RESET
                + clienName
                + Constants.SYSTEM_CRITICAL_MESSAGE_COLOR
                + " is already taken. Try again with another username ###\n"
                + Constants.COLOR_RESET);
    }
}
