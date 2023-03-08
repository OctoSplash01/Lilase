package me.night0721.lilase.config;

import me.night0721.lilase.utils.Utils;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigUtils {
    public static Configuration config;
    public final static String file = "config/Lilase.cfg";

    public static void register() {
        init();
        reloadConfig();
    }

    public static void checkWebhookAndAPI() {
        if (getString("main", "APIKey").equals("") || getString("main", "Webhook").equals("")) {
            Utils.sendMessage("API Key or Webhook is not set, please set it in the menu (Press *)");
        }
    }

    public static void reloadConfig() {
        if (hasNoKey("main", "APIKey")) writeStringConfig("main", "APIKey", "");
        if (hasNoKey("main", "SendMessageToWebhook")) writeBooleanConfig("main", "SendMessageToWebhook", true);
        if (hasNoKey("main", "Webhook")) writeStringConfig("main", "Webhook", "");
        if (hasNoKey("main", "ReconnectDelay")) writeIntConfig("main", "ReconnectDelay", 20);
        if (hasNoKey("main", "AuctionHouseDelay")) writeIntConfig("main", "AuctionHouseDelay", 8);
        if (hasNoKey("main", "BedSpam")) writeBooleanConfig("main", "BedSpam", true);
        if (hasNoKey("main", "BedSpamDelay")) writeIntConfig("main", "BedSpamDelay", 100);
        if (hasNoKey("main", "OnlySniper")) writeBooleanConfig("main", "OnlySniper", false);
        if (hasNoKey("main", "checkProfitPercentageBeforeBuy"))
            writeBooleanConfig("main", "checkProfitPercentageBeforeBuy", false);
        if (hasNoKey("main", "MinimumProfitPercentage")) writeIntConfig("main", "MinimumProfitPercentage", 400); //400%
        if (hasNoKey("main", "GUI")) writeBooleanConfig("main", "GUI", true);
        if (hasNoKey("main", "GUI_COLOR")) writeIntConfig("main", "GUI_COLOR", 0x003153);
        for (int i = 1; i <= 3; i++) {
            if (hasNoKey("item" + i, "Name")) writeStringConfig("item" + i, "Name", "");
            if (hasNoKey("item" + i, "Type")) writeStringConfig("item" + i, "Type", "");
            if (hasNoKey("item" + i, "Price")) writeIntConfig("item" + i, "Price", 0);
            if (hasNoKey("item" + i, "Tier")) writeStringConfig("item" + i, "Tier", "");
        }
        for (int i = 1; i <= 3; i++) {
            if (hasNoKey("blacklist" + i, "Name")) writeStringConfig("blacklist" + i, "Name", "");
            if (hasNoKey("blacklist" + i, "Type")) writeStringConfig("blacklist" + i, "Type", "");
            if (hasNoKey("blacklist" + i, "Price")) writeIntConfig("blacklist" + i, "Price", 0);
            if (hasNoKey("blacklist" + i, "Tier")) writeStringConfig("blacklist" + i, "Tier", "");
        }
    }

    public static void init() {
        config = new Configuration(new File(file));
        try {
            config.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }


    public static int getInt(String category, String key) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0).getInt();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return 0;
    }

    public static double getDouble(String category, String key) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0D).getDouble();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return 0D;
    }

    public static String getString(String category, String key) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, "").getString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return "";
    }

    public static boolean getBoolean(String category, String key) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, false).getBoolean();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return true;
    }

    public static void writeIntConfig(String category, String key, int value) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
//            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeDoubleConfig(String category, String key, double value) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
//            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeStringConfig(String category, String key, String value) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
//            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeBooleanConfig(String category, String key, boolean value) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
//            boolean set = config.get(category, key, value).getBoolean();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static boolean hasNoKey(String category, String key) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
            if (!config.hasCategory(category)) return true;
            return !config.getCategory(category).containsKey(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return true;
    }

    public static void deleteCategory(String category) {
        category = category.toLowerCase();
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.hasCategory(category)) {
                config.removeCategory(new ConfigCategory(category));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }
}