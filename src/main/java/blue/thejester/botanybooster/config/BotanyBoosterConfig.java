package blue.thejester.botanybooster.config;

import blue.thejester.botanybooster.BotanyBooster;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Config(modid=BotanyBooster.MODID, name="botany-booster")
public class BotanyBoosterConfig {
    @Config.Comment(value={"Configuration for the Orechid Cucntus","Lines should be in the form stonemod:stoneid|oredict","for example, vanilla stone->gold would be 'minecraft:stone|oreGold|1000'"})
    public static String[] orechidCunctusConfig = {};
}
