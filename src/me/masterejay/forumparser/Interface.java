package me.masterejay.forumparser;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * @author MasterEjay
 */
public class Interface extends JFrame {

    private JPanel contentPane;
    JButton dump;

    public static void main(String[] args){
        Interface i = new Interface();
        i.setVisible(true);
        ForumParser.setupSchedule();
    }

    public Interface(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(100, 100, 200, 100);
        contentPane = new JPanel();
        setTitle("ForumParser");
        dump = new JButton("Dump thread titles");
        dump.setBounds(20,20,150,30);
        add(dump);
        dump.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileUtils.save("Dump", ForumParser.titleList);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        try {
            new ForumParser().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void actionPerformed(ActionEvent e) {
        try {
            FileUtils.save("Dump.txt", ForumParser.titleList);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
