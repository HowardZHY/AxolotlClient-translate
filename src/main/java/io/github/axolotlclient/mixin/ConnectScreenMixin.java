package io.github.axolotlclient.mixin;

import io.github.axolotlclient.util.FeatureDisabler;
import net.minecraft.client.gui.screen.ConnectScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public abstract class ConnectScreenMixin {

    @Inject(method = "connect", at = @At("HEAD"))
    private void onServerJoin(String address, int port, CallbackInfo ci){
        FeatureDisabler.onServerJoin(address);
    }
}