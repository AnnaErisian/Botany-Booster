package blue.thejester.botanybooster.core;

import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import blue.thejester.botanybooster.block.subtile.functional.SubTileOrechidCunctus;
import blue.thejester.botanybooster.recipe.ModPetalRecipe;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;

public class LexiconData {

    public static LexiconEntry dryacinth;
    public static LexiconEntry orechidCunctus;

    public static void init() {
        dryacinth = new BasicLexiconEntry(SubTileDryacinth.NAME, BotaniaAPI.categoryFunctionalFlowers);
        dryacinth.setLexiconPages(
                BotaniaAPI.internalHandler.textPage("botania.page.dryacinth.0"),
                BotaniaAPI.internalHandler.petalRecipePage("1", ModPetalRecipe.dryacinthRecipe)
        );

        orechidCunctus = new BasicLexiconEntry(SubTileOrechidCunctus.NAME, BotaniaAPI.categoryFunctionalFlowers);
        orechidCunctus.setLexiconPages(
                BotaniaAPI.internalHandler.textPage("botania.page.orechidCunctus.0"),
                BotaniaAPI.internalHandler.petalRecipePage("1", ModPetalRecipe.orechidCunctusRecipe)
        );
    }

}
