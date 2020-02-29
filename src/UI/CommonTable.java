package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class CommonTable extends JTable {

    public CommonTable(TableModel model) {
        super(model);
        //设置表格只能选择一行
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //设置表格行高
        this.setRowHeight(30);

        this.getTableHeader().setFont(new Font(null, Font.BOLD, 14));

        //表头文字居中
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);

        //表内文字居中
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        this.setDefaultRenderer(Object.class, r);
    }

    //使所有单元格不可编辑
    public boolean isCellEditable(int row, int column) {
        return false;
    }


    //判断新的学号是否重复
    public boolean isSame(JTable table, String s) {
        for (int row=0; row < table.getRowCount(); row++) {
            if (s.equals(table.getValueAt(row, 0).toString())) return true;
        }
        return false;
    }
}
