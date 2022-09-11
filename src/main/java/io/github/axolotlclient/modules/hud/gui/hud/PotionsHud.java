package io.github.axolotlclient.modules.hud.gui.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.axolotlclient.config.options.OptionBase;
import io.github.axolotlclient.modules.hud.gui.AbstractHudEntry;
import io.github.axolotlclient.modules.hud.util.DrawPosition;
import io.github.axolotlclient.util.Util;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;

/**
 * This implementation of Hud modules is based on KronHUD.
 * <a href="https://github.com/DarkKronicle/KronHUD">Github Link.</a>
 * @license GPL-3.0
 */

public class PotionsHud extends AbstractHudEntry {

    public static final Identifier ID = new Identifier("kronhud", "potionshud");
    protected static final Identifier INVENTORY_TEXTURE = new Identifier("textures/gui/container/inventory.png");

    public PotionsHud() {
        super(60, 200);
    }

    @Override
    public void render() {

        GlStateManager.enableBlend();

        scale();
        DrawPosition pos = getPos();
        int i = pos.x-2;
        int y = pos.y;
        Collection<StatusEffectInstance> collection = this.client.player.getStatusEffectInstances();
        if (!collection.isEmpty()) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int l = 33;
            if (collection.size() > 5) {
                l = 132 / (collection.size() - 1);
            }
            for(StatusEffectInstance statusEffectInstance : collection) {
                StatusEffect statusEffect = StatusEffect.STATUS_EFFECTS[statusEffectInstance.getEffectId()];
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.client.getTextureManager().bindTexture(INVENTORY_TEXTURE);
                if (statusEffect.method_2443()) {
                    int m = statusEffect.method_2444();
                    this.drawTexture(i + 6, y + 6, m % 8 * 18, 198 + m / 8 * 18, 18, 18);
                }

                String string = I18n.translate(statusEffect.getTranslationKey());
                 if (statusEffectInstance.getAmplifier() != 1) {
                    string += " " + Util.toRoman(statusEffectInstance.getAmplifier()+1);
                }

                client.textRenderer.drawWithShadow(string, (float)(i + 10 + 18), (float)(y + 6), textColor.get().getAsInt());
                String string2 = StatusEffect.method_2436(statusEffectInstance);
                client.textRenderer.drawWithShadow(string2, (float)(i + 10 + 18), (float)(y + 6 + 10), 8355711);
                y += l;
            }

        }
        GlStateManager.popMatrix();

    }

    @Override
    public void renderPlaceholder() {

        renderPlaceholderBackground();
        scale();
        DrawPosition pos = getPos();
        int i = pos.x-2;
        StatusEffect statusEffect = StatusEffect.STATUS_EFFECTS[1];
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(INVENTORY_TEXTURE);
        int m = statusEffect.method_2444();
        this.drawTexture(i + 6, pos.y + 7, m % 8 * 18, 198 + m / 8 * 18, 18, 18);
        client.textRenderer.drawWithShadow(I18n.translate(statusEffect.getTranslationKey()), (float)(i + 10 + 18), (float)(pos.y + 6), 16777215);
        client.textRenderer.drawWithShadow("**:**", (float)(i + 10 + 18), (float)(pos.y + 6 + 10), textColor.get().getAsInt());
        GlStateManager.popMatrix();
        hovered = false;

    }

    @Override
    public void addConfigOptions(List<OptionBase<?>> options) {
        super.addConfigOptions(options);
        options.add(textColor);
        options.add(shadow);
    }

    @Override
    public boolean movable() {
        return true;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

}
