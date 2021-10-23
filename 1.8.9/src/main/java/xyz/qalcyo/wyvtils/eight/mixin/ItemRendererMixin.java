/*
 * Wyvtils, a utility mod for 1.8.9.
 * Copyright (C) 2021 Wyvtils
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.qalcyo.wyvtils.eight.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.qalcyo.wyvtils.core.config.WyvtilsConfig;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow private ItemStack itemToRender;

    //Original code from Terbium by Deftu
    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V"))
    protected void onItemInFirstPersonRendered(float partialTicks, CallbackInfo ci) {
        if (WyvtilsConfig.INSTANCE.getSwapBow() && itemToRender.getItemUseAction() == EnumAction.BOW) {
            if (!WyvtilsConfig.INSTANCE.getLeftHand()) {
                GL11.glScaled(-1.0d, 1.0d, 1.0d);
                GlStateManager.disableCull();
            }
        } else {
            if (WyvtilsConfig.INSTANCE.getLeftHand()) {
                GL11.glScaled(-1.0d, 1.0d, 1.0d);
                GlStateManager.disableCull();
            }
        }
    }
}
