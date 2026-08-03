package ui.components;

import ui.theme.AppColors;
import ui.theme.AppFonts;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TableStyle {

    private TableStyle() {
    }

    public static void apply(JTable table) {

        //-------------------------
        // Fuente
        //-------------------------

        table.setFont(AppFonts.TABLE);

        table.setForeground(AppColors.TEXT_PRIMARY);

        table.setRowHeight(34);

        table.setGridColor(AppColors.TABLE_GRID);

        table.setSelectionBackground(AppColors.TABLE_SELECTION);

        table.setSelectionForeground(AppColors.TEXT_PRIMARY);

        table.setShowHorizontalLines(true);

        table.setShowVerticalLines(false);

        table.setIntercellSpacing(new Dimension(0,1));

        table.setFillsViewportHeight(true);

        table.setFocusable(false);

        //-------------------------
        // Header
        //-------------------------

        JTableHeader header = table.getTableHeader();

        header.setFont(AppFonts.TABLE_HEADER);

        header.setBackground(AppColors.TABLE_HEADER);

        header.setForeground(AppColors.TABLE_HEADER_TEXT);

        header.setOpaque(false);

        header.setReorderingAllowed(false);

        header.setPreferredSize(new Dimension(0,38));

        header.setDefaultRenderer(new HeaderRenderer());

        //-------------------------
        // Render de filas
        //-------------------------

        table.setDefaultRenderer(Object.class,
                new TableRowRenderer());

    }

    /**
     * Header
     */

    private static class HeaderRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column
            );

            label.setHorizontalAlignment(CENTER);

            label.setFont(AppFonts.TABLE_HEADER);

            label.setForeground(AppColors.TABLE_HEADER_TEXT);

            label.setBackground(AppColors.TABLE_HEADER);

            label.setOpaque(true);

            return label;

        }

    }

    /**
     * Filas
     */

    private static class TableRowRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column
            );

            label.setBorder(BorderFactory.createEmptyBorder(
                    0,
                    10,
                    0,
                    10
            ));

            if (isSelected) {

                label.setBackground(AppColors.TABLE_SELECTION);

                label.setForeground(AppColors.TEXT_PRIMARY);

            } else {

                if (row % 2 == 0) {

                    label.setBackground(AppColors.TABLE_ROW);

                } else {

                    label.setBackground(AppColors.TABLE_ROW_ALTERNATE);

                }

                label.setForeground(AppColors.TEXT_PRIMARY);

            }

            return label;

        }

    }

}