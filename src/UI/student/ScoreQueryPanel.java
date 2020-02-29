package UI.student;

import Bean.Score;
import Service.ScoreService;
import UI.CommonTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ScoreQueryPanel extends JPanel {

    public ScoreQueryPanel(int studentId) {

        ScoreService scoreService = new ScoreService();
        Score score = scoreService.getScoreService(studentId);

        Object[] col = {"学号", "姓名", "语文", "数学", "英语", "总分"};
        DefaultTableModel model = new DefaultTableModel(new Object[1][6], col);
        CommonTable table = new CommonTable(model);

        Object[] rowData = {
                score.getStudentId(),
                score.getStudentName(),
                score.getChinese(),
                score.getMath(),
                score.getEnglish(),
                score.getSum()
        };

        for (int i=0; i < 6; i++) {
            model.setValueAt(rowData[i], 0, i);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 645));

        this.add(scrollPane);
    }
}
