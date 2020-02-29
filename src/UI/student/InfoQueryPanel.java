package UI.student;

import Bean.Info;
import Service.InfoService;
import UI.CommonTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InfoQueryPanel extends JPanel {

    public InfoQueryPanel(int studentId) {

        InfoService infoService = new InfoService();
        Info info = infoService.getInfoService(studentId);

        Object[] col = {"学号", "姓名", "性别", "专业", "年级", "生日"};
        DefaultTableModel model = new DefaultTableModel(new Object[1][6], col);
        CommonTable table = new CommonTable(model);

        Object[] rowData = {
                info.getStudentId(),
                info.getStudentName(),
                info.getGender(),
                info.getMajor(),
                info.getGrade(),
                info.getBirth()
        };

        for (int i=0; i < 6; i++) {
            model.setValueAt(rowData[i], 0, i);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 645));

        this.add(scrollPane);
    }
}
