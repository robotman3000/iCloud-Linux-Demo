package icloud.gui.acount;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTree;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JEditorPane;

public class AccountDetails {

	private JFrame frmUserPropertiesViewer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountDetails window = new AccountDetails();
					window.frmUserPropertiesViewer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AccountDetails() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUserPropertiesViewer = new JFrame();
		frmUserPropertiesViewer.setTitle("User Properties Viewer");
		frmUserPropertiesViewer.setBounds(100, 100, 700, 450);
		frmUserPropertiesViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frmUserPropertiesViewer.getContentPane().setLayout(gridBagLayout);
		
		JDesktopPane desktopPane = new JDesktopPane();
		GridBagConstraints gbc_desktopPane = new GridBagConstraints();
		gbc_desktopPane.fill = GridBagConstraints.BOTH;
		gbc_desktopPane.gridx = 0;
		gbc_desktopPane.gridy = 0;
		frmUserPropertiesViewer.getContentPane().add(desktopPane, gbc_desktopPane);
		
		JInternalFrame internalFrame = new JInternalFrame("Properties");
		internalFrame.setResizable(true);
		internalFrame.setIconifiable(true);
		internalFrame.setMaximizable(true);
		internalFrame.setBounds(12, 12, 182, 399);
		desktopPane.add(internalFrame);
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{0, 0};
		gridBagLayout_1.rowHeights = new int[]{0, 0};
		gridBagLayout_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		internalFrame.getContentPane().setLayout(gridBagLayout_1);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		internalFrame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JList propertiesList = new JList();
		scrollPane.setViewportView(propertiesList);
		
		JInternalFrame internalFrame_1 = new JInternalFrame("Viewer");
		internalFrame_1.setResizable(true);
		internalFrame_1.setIconifiable(true);
		internalFrame_1.setMaximizable(true);
		internalFrame_1.setBounds(206, 12, 478, 399);
		desktopPane.add(internalFrame_1);
		GridBagLayout gridBagLayout_2 = new GridBagLayout();
		gridBagLayout_2.columnWidths = new int[]{0, 0};
		gridBagLayout_2.rowHeights = new int[]{0, 0};
		gridBagLayout_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		internalFrame_1.getContentPane().setLayout(gridBagLayout_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		internalFrame_1.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		JEditorPane propViewer = new JEditorPane();
		propViewer.setEditable(false);
		scrollPane_1.setViewportView(propViewer);
		internalFrame_1.setVisible(true);
		internalFrame.setVisible(true);
	}
}
