package io.github.axolotlclient.modules.hud.gui.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.axolotlclient.config.Color;
import io.github.axolotlclient.config.options.BooleanOption;
import io.github.axolotlclient.config.options.OptionBase;
import io.github.axolotlclient.modules.hud.gui.AbstractHudEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * This implementation of Hud modules is based on KronHUD.
 * <a href="https://github.com/DarkKronicle/KronHUD">Github Link.</a>
 * @license GPL-3.0
 */

public class ActionBarHud extends AbstractHudEntry {

    public static final Identifier ID = new Identifier("kronhud", "actionbarhud");
    public final BooleanOption customTextColor = new BooleanOption("customtextcolor", false);

    private String actionBar;
    private int color;
    private final Color current = new Color(-1);
    private final Color vanillaColor = new Color(-1);
    MinecraftClient client;
    private String placeholder;

    public ActionBarHud() {
        super(115, 13);
        client = MinecraftClient.getInstance();
    }

    public void setActionBar(String bar, int color){this.actionBar = bar; this.color = color;}

    @Override
    public void render() {
        if (vanillaColor.setData(color).getAlpha()<=0){
            this.actionBar = null;
        }
        if(this.actionBar != null) {

            scale();
            drawString(actionBar, getPos().x, getPos().y + 3, customTextColor.get() ? (textColor.get().getAlpha()==255 ?
                    current.setData(
                            textColor.get().getRed(),
                            textColor.get().getGreen(),
                            textColor.get().getBlue(),
                            vanillaColor.getAlpha()):
                    textColor.get()) :
                    vanillaColor, shadow.get());
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void renderPlaceholder() {
        if(placeholder == null){
            placeholder = I18n.translate("actionBarPlaceholder");
        }
        renderPlaceholderBackground();
        scale();
        drawString(placeholder,
                getPos().x + Math.round(width /2F) - client.textRenderer.getStringWidth(placeholder) /2,
                getPos().y + 3,
                Color.WHITE, shadow.get());
        GlStateManager.popMatrix();
        hovered = false;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public boolean movable() {
        return true;
    }

    @Override
    public void addConfigOptions(List<OptionBase<?>> options){
        super.addConfigOptions(options);
        options.add(shadow);
        options.add(textAlignment);
        options.add(customTextColor);
        options.add(textColor);
    }
}
