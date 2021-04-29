package habr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiForm {
    private JPanel center;
    private JPanel east;
    private JPanel mainPanel;

    private JButton submitBtn;
    private JList listView;

    private Parser parser;

    public GuiForm() {
        //Create all components
        JFrame form=new JFrame("Habr Articles");
        mainPanel=new JPanel();
        center=new JPanel();
        east=new JPanel();
        submitBtn=new JButton("Enter");
        listView=new JList();

        //Laying out components on panels
        parser=new Parser("https://habr.com/ru/");
        submitBtn.addMouseListener(new MouseClicker());
        center.add(listView);
        east.add(submitBtn);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(center,BorderLayout.CENTER);
        mainPanel.add(east,BorderLayout.EAST);
        //Adding all components to the form and set size
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.setSize(new Dimension(350,250));
        form.add(mainPanel);
        form.setVisible(true);
    }
    class MouseClicker extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            listView.setListData(parser.parseArticleTitles().toArray());
        }
    }
}
