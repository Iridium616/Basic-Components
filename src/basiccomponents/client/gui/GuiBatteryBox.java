package basiccomponents.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.core.electricity.ElectricityDisplay.ElectricUnit;
import basiccomponents.common.BasicComponents;
import basiccomponents.common.container.ContainerBatteryBox;
import basiccomponents.common.tileentity.TileEntityBatteryBox;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBatteryBox extends GuiContainer
{
	private static final ResourceLocation batteryBoxTexture = new ResourceLocation(BasicComponents.TEXTURE_DOMAIN, "textures/gui/battery_box.png");

	private TileEntityBatteryBox tileEntity;

	private int containerWidth;
	private int containerHeight;

	public GuiBatteryBox(InventoryPlayer par1InventoryPlayer, TileEntityBatteryBox batteryBox)
	{
		super(new ContainerBatteryBox(par1InventoryPlayer, batteryBox));
		this.tileEntity = batteryBox;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString(this.tileEntity.getInvName(), 65, 6, 4210752);
		String displayJoules = ElectricityDisplay.getDisplayShort(tileEntity.getEnergyStored(), ElectricUnit.JOULES) + " of";
		String displayMaxJoules = ElectricityDisplay.getDisplay(tileEntity.getMaxEnergyStored(), ElectricUnit.JOULES);
		String displayVoltage = "Voltage: " + (int) (this.tileEntity.getVoltage() * 1000);
		
		this.fontRenderer.drawString(displayJoules, 122 - this.fontRenderer.getStringWidth(displayJoules) / 2, 30, 4210752);
		this.fontRenderer.drawString(displayMaxJoules, 122 - this.fontRenderer.getStringWidth(displayMaxJoules) / 2, 40, 4210752);
		this.fontRenderer.drawString(displayVoltage, 122 - this.fontRenderer.getStringWidth(displayVoltage) / 2, 60, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.func_110577_a(batteryBoxTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.containerWidth = (this.width - this.xSize) / 2;
		this.containerHeight = (this.height - this.ySize) / 2;
		// Background energy bar
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
		// Foreground energy bar
		int scale = (int) ((this.tileEntity.getEnergyStored() / this.tileEntity.getMaxEnergyStored()) * 72);
		this.drawTexturedModalRect(containerWidth + 87, containerHeight + 52, 176, 0, scale, 20);
	}
}
