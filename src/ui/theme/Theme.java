package ui.theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;

public final class Theme {

    private Theme() {
    }

    public static void apply() {

        try {

            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        //-----------------------------
        // Paneles
        //-----------------------------

        UIManager.put("Panel.background",
                AppColors.BACKGROUND);

        //-----------------------------
        // Labels
        //-----------------------------

        UIManager.put("Label.font",
                AppFonts.LABEL);

        UIManager.put("Label.foreground",
                AppColors.TEXT_PRIMARY);

        //-----------------------------
        // Botones
        //-----------------------------

        UIManager.put("Button.font",
                AppFonts.BUTTON);

        UIManager.put("Button.focus",
                new Color(0,0,0,0));

        //-----------------------------
        // TextField
        //-----------------------------

        UIManager.put("TextField.font",
                AppFonts.TEXT_FIELD);

        UIManager.put("TextField.background",
                Color.WHITE);

        UIManager.put("TextField.foreground",
                AppColors.TEXT_PRIMARY);

        UIManager.put("TextField.caretForeground",
                AppColors.PRIMARY);

        UIManager.put("TextField.border",
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                AppColors.BORDER
                        ),

                        new EmptyBorder(8,10,8,10)

                ));

        //-----------------------------
        // ComboBox
        //-----------------------------

        UIManager.put("ComboBox.font",
                AppFonts.COMBO);

        UIManager.put("ComboBox.background",
                Color.WHITE);

        UIManager.put("ComboBox.foreground",
                AppColors.TEXT_PRIMARY);

        //-----------------------------
        // CheckBox
        //-----------------------------

        UIManager.put("CheckBox.font",
                AppFonts.REGULAR);

        UIManager.put("CheckBox.background",
                AppColors.BACKGROUND);

        //-----------------------------
        // RadioButton
        //-----------------------------

        UIManager.put("RadioButton.font",
                AppFonts.REGULAR);

        UIManager.put("RadioButton.background",
                AppColors.BACKGROUND);

        //-----------------------------
        // ToolTip
        //-----------------------------

        UIManager.put("ToolTip.font",
                AppFonts.SMALL);

        //-----------------------------
        // OptionPane
        //-----------------------------

        UIManager.put("OptionPane.background",
                AppColors.WARNING);

        UIManager.put("Panel.background",
                AppColors.BACKGROUND);

        UIManager.put("OptionPane.messageFont",
                AppFonts.REGULAR);

        UIManager.put("OptionPane.buttonFont",
                AppFonts.BUTTON);

        //-----------------------------
        // ScrollPane
        //-----------------------------

        UIManager.put("ScrollPane.background",
                Color.WHITE);

        UIManager.put("Viewport.background",
                Color.WHITE);

        //-----------------------------
        // Separadores
        //-----------------------------

        UIManager.put("Separator.foreground",
                AppColors.BORDER);

        UIManager.put("Button.arc", 14);

        UIManager.put("Component.arc", 14);

        UIManager.put("TextComponent.arc", 14);

        UIManager.put("ScrollBar.width", 12);

        UIManager.put("ScrollBar.thumbArc", 999);

        UIManager.put("TabbedPane.tabHeight", 36);

        UIManager.put("TitlePane.unifiedBackground", true);

        UIManager.put("TitlePane.background", AppColors.BACKGROUND);

        UIManager.put("TitlePane.foreground", AppColors.TEXT_PRIMARY);

    }

}