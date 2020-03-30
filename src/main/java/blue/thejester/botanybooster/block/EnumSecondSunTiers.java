package blue.thejester.botanybooster.block;

import net.minecraft.util.IStringSerializable;

public enum EnumSecondSunTiers implements IStringSerializable {
    zero("zero"),
    one("one"),
    two("two"),
    three("three"),
    four("four"),
    five("five"),
    six("six");

    @Override
    public String getName() {
        return name;
    }

    public static EnumSecondSunTiers fromMeta(int meta)
    {
        if (meta < 0 || meta >= values().length) {
            meta = 0;
        }
        return values()[meta];
    }

    private final String name;

    EnumSecondSunTiers(String i_name)
    {
        this.name = i_name;
    }
}
