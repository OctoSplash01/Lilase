package me.night0721.lilase.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InventoryUtils {
    static int opened = 0;
    /*
     *  @Author Mostly Apfelsaft
     */
    private static final Minecraft mc = Minecraft.getMinecraft();


    public static boolean isToAuctionItem(String name) {
        final ItemStack stack = mc.thePlayer.openContainer.getSlot(13).getStack();
        if (stack != null && stack.hasTagCompound()) {
            final NBTTagCompound tag = stack.getTagCompound();
            final Pattern pattern = Pattern.compile(name, Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(tag.toString());
            while (matcher.find()) {
                if (matcher.group(0) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isStoneButton() {
        final ItemStack stack = mc.thePlayer.openContainer.getSlot(13).getStack();
        if (stack != null && stack.hasTagCompound()) {
            final NBTTagCompound tag = stack.getTagCompound();
            final Pattern pattern = Pattern.compile("Click an item in your inventory!", Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(tag.toString());
            while (matcher.find()) {
                if (matcher.group(0) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getInventoryName() {
        if (InventoryUtils.mc.currentScreen instanceof GuiChest) {
            final ContainerChest chest = (ContainerChest) InventoryUtils.mc.thePlayer.openContainer;
            final IInventory inv = chest.getLowerChestInventory();
            return inv.hasCustomName() ? inv.getName() : null;
        }
        return null;
    }

    public static boolean inventoryNameStartsWith(String startsWithString) {
        return InventoryUtils.getInventoryName() != null && InventoryUtils.getInventoryName().startsWith(startsWithString);
    }

    public static boolean inventoryNameContains(String startsWithString) {
        return InventoryUtils.getInventoryName() != null && InventoryUtils.getInventoryName().contains(startsWithString);
    }

    public static void openInventory() {
        mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
    }

    public static ItemStack getStackInSlot(final int slot) {
        return InventoryUtils.mc.thePlayer.inventory.getStackInSlot(slot);
    }

    public static ItemStack getStackInOpenContainerSlot(final int slot) {
        if (InventoryUtils.mc.thePlayer.openContainer.inventorySlots.get(slot).getHasStack()) {
            return InventoryUtils.mc.thePlayer.openContainer.inventorySlots.get(slot).getStack();
        }
        return null;
    }

    public static int getSlotForItem(final String itemName) {
        for (final Slot slot : mc.thePlayer.openContainer.inventorySlots) {
            if (slot.getHasStack()) {
                final ItemStack is = slot.getStack();
                if (is.getDisplayName().contains(itemName)) {
                    return slot.slotNumber;
                }
            }
        }
        return -1;
    }

    public static void clickOpenContainerSlot(final int slot, final int button, final int clickType) {
        mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, slot, button, clickType, mc.thePlayer);
    }

    public static void clickOpenContainerSlot(final int slot, final int button) {
        clickOpenContainerSlot(slot, button, 0);
    }

    public static void clickOpenContainerSlot(final int slot) {
        clickOpenContainerSlot(slot, 0, 0);
    }


    public static int getAvailableHotbarSlot(final String name) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack is = mc.thePlayer.inventory.getStackInSlot(i);
            if (is == null || is.getDisplayName().contains(name)) {
                return i;
            }
        }
        return -1;
    }

    public static List<Integer> getAllSlots(final String name) {
        final List<Integer> ret = new ArrayList<>();
        for (int i = 9; i < 44; ++i) {
            final ItemStack is = mc.thePlayer.inventoryContainer.inventorySlots.get(i).getStack();
            if (is != null && is.getDisplayName().contains(name)) {
                ret.add(i);
            }
        }
        return ret;
    }

    public static int getAmountInHotbar(final String item) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack is = InventoryUtils.mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && StringUtils.stripControlCodes(is.getDisplayName()).equals(item)) {
                return is.stackSize;
            }
        }
        return 0;
    }

    public static int getItemInHotbar(final String itemName) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack is = InventoryUtils.mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && StringUtils.stripControlCodes(is.getDisplayName()).contains(itemName)) {
                return i;
            }
        }
        return -1;
    }

    public static List<ItemStack> getInventoryStacks() {
        final List<ItemStack> ret = new ArrayList<ItemStack>();
        for (int i = 9; i < 44; ++i) {
            final Slot slot = InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i);
            if (slot != null) {
                final ItemStack stack = slot.getStack();
                if (stack != null) {
                    ret.add(stack);
                }
            }
        }
        return ret;
    }

    public static List<Slot> getInventorySlots() {
        final List<Slot> ret = new ArrayList<>();
        for (int i = 9; i < 44; ++i) {
            final Slot slot = InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i);
            if (slot != null) {
                final ItemStack stack = slot.getStack();
                if (stack != null) {
                    ret.add(slot);
                }
            }
        }
        return ret;
    }


    public static NBTTagCompound getExtraAttributes(ItemStack item) {
        if (item == null) {
            throw new NullPointerException("The item cannot be null!");
        }
        if (!item.hasTagCompound()) {
            return null;
        }

        return item.getSubCompound("ExtraAttributes", false);
    }

    public static NBTTagList getLore(ItemStack item) {
        if (item == null) {
            throw new NullPointerException("The item cannot be null!");
        }
        if (!item.hasTagCompound()) {
            return null;
        }

        return item.getSubCompound("display", false).getTagList("Lore", 8);
    }
}