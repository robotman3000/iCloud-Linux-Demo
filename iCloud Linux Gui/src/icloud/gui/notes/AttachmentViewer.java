/*
 * Decompiled with CFR 0_97.
 */
package icloud.gui.notes;

import io.github.robotman3000.cloudnix.services.notes.NoteManager;
import io.github.robotman3000.cloudnix.user.UserSession;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class AttachmentViewer
extends JFrame
implements Runnable {
    private static final long serialVersionUID = 8925110973025133029L;
    private String contentType;
    private String attachmentID;
    private JLabel jEditorPane1;
    private JScrollPane jScrollPane1;
	private UserSession myUser;

    public AttachmentViewer(UserSession user, String contentType, String attachmentID) {
        this.contentType = contentType;
        this.attachmentID = attachmentID;
        this.myUser = user;
        this.initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.jEditorPane1 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jScrollPane1.setViewportView(this.jEditorPane1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 437, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 328, 32767));
        this.pack();
    }

    public static void main(String[] args) {
    }

    @Override
    public void run() {
        try {
        	NoteManager noteMan = new NoteManager();
            BufferedImage bi = noteMan.retriveAttachment(myUser, this.attachmentID);
            ImageIcon icon = new ImageIcon(bi);
            this.jEditorPane1.setIcon(icon);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.setVisible(true);
    }
}

