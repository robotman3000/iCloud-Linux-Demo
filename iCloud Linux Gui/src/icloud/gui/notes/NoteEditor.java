package icloud.gui.notes;

import io.github.robotman3000.cloudnix.services.notes.NoteManager;
import io.github.robotman3000.cloudnix.services.notes.objects.Attachment;
import io.github.robotman3000.cloudnix.services.notes.objects.Note;
import io.github.robotman3000.cloudnix.user.UserSession;
import common.CommonLogic;

import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.swing.ListSelectionModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class NoteEditor {

	private JFrame frmNoteEditor;
	private JList<Note> noteListGui;
	private JEditorPane editorPane;

	private NoteManager noteManager = new NoteManager(true, true);
	DefaultListModel<Note> noteList;
	DefaultListModel<Attachment> attachmentList;
	private UserSession user;
	private JTextArea rawNoteGui;
	private JList<Attachment> attachmnentListGui;
	private JLabel attachViewer;
	private JEditorPane renderedNoteGui;

	/**
	 * Create the application.
	 */
	public NoteEditor(UserSession userNew) {
		initialize();
		this.user = userNew;
		// frmNoteEditor.getContentPane().setLayout(null);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 47, 796, 476);
		GridBagConstraints gbc_desktopPane = new GridBagConstraints();
		gbc_desktopPane.fill = GridBagConstraints.BOTH;
		gbc_desktopPane.gridx = 0;
		gbc_desktopPane.gridy = 0;
		frmNoteEditor.getContentPane().add(desktopPane, gbc_desktopPane);

		JInternalFrame internalFrame = new JInternalFrame("Editor Window");
		internalFrame.setBounds(224, 12, 500, 499);
		internalFrame.setVisible(true);
		internalFrame.setResizable(true);
		internalFrame.setIconifiable(true);
		internalFrame.setMaximizable(true);

		JInternalFrame internalFrame_1 = new JInternalFrame("Notes List");
		internalFrame_1.setBounds(12, 12, 200, 252);
		internalFrame_1.setVisible(true);
		desktopPane.setLayout(null);
		internalFrame_1.setResizable(true);
		internalFrame_1.setIconifiable(true);
		internalFrame_1.setMaximizable(true);
		desktopPane.add(internalFrame_1);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 490, 0 };
		gridBagLayout.rowHeights = new int[] { 462, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		internalFrame.getContentPane().setLayout(gridBagLayout);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		internalFrame.getContentPane().add(tabbedPane, gbc_tabbedPane);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Note Editor", null, panel_2, null);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 189, 106, 0 };
		gbl_panel_2.rowHeights = new int[] { 21, 0 };
		gbl_panel_2.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridwidth = 2;
		gbc_scrollPane_2.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		panel_2.add(scrollPane_2, gbc_scrollPane_2);

		editorPane = new JEditorPane();
		scrollPane_2.setViewportView(editorPane);
		editorPane.setContentType("text/plain");

		JPanel panel = new JPanel();
		tabbedPane.addTab("Raw Note", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);

		rawNoteGui = new JTextArea();
		scrollPane.setViewportView(rawNoteGui);
		rawNoteGui.setEditable(false);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Rendered Note", null, panel_3, null);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 0;
		gbc_scrollPane_3.gridy = 0;
		panel_3.add(scrollPane_3, gbc_scrollPane_3);

		renderedNoteGui = new JEditorPane();
		renderedNoteGui.setContentType("text/html");
		renderedNoteGui.setEditable(false);
		scrollPane_3.setViewportView(renderedNoteGui);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Attachment", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 242, 1, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 1, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);

		attachViewer = new JLabel("");
		scrollPane_1.setViewportView(attachViewer);
		desktopPane.add(internalFrame);

		JInternalFrame internalFrame_2 = new JInternalFrame("Tool Box");
		internalFrame_2.setBounds(734, 12, 200, 499);
		desktopPane.add(internalFrame_2);
		internalFrame_2.setIconifiable(true);
		internalFrame_2.setMaximizable(true);
		internalFrame_2.getContentPane().setLayout(null);

		JButton btnNewNote = new JButton("New Note");
		btnNewNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar currCalendar = Calendar.getInstance();
				Date currDate = currCalendar.getTime();
				int year = currCalendar.get(1);
				System.out.println("Year: " + year);
				int month = currCalendar.get(2);
				System.out.println("Month: " + month);
				int day = currCalendar.get(5);
				System.out.println("Day: " + day);
				int hour = currCalendar.get(11);
				System.out.println("Hour: " + hour);
				int minute = currCalendar.get(12);
				System.out.println("Minute: " + minute);
				int second = currCalendar.get(13);
				System.out.println("Second: " + second);
				int timeOffSet = currCalendar.get(15);
				System.out.println("Time Zone: " + timeOffSet);
				Note newNote = new Note();
				newNote.setDateModified(new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ssXXX").format(currDate));
				newNote.setContent("New Note");
				newNote.setFolderName("/");
				newNote.setNoteID(CommonLogic.generateUUID());
				newNote.setSize("0");
				newNote.setSubject("New Note");

				try {
					updateNotesList();
					noteManager.createNotes(user, newNote);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				updateNotesList();
			}
		});
		btnNewNote.setBounds(13, 12, 163, 25);
		internalFrame_2.getContentPane().add(btnNewNote);

		JButton btnSaveNote = new JButton("Save Note");
		btnSaveNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Note updateNote = noteListGui.getSelectedValue();
					System.out.println("Editor Text: " + editorPane.getText());
					updateNote.setContent(editorPane.getText());
					noteManager.updateNotes(user, updateNote);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateNotesList();
			}
		});
		btnSaveNote.setBounds(13, 49, 163, 25);
		internalFrame_2.getContentPane().add(btnSaveNote);

		JButton btnDeleteNote = new JButton("Delete Note");
		btnDeleteNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					noteManager.deleteNotes(user,
							noteListGui.getSelectedValue());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateNotesList();
			}
		});
		btnDeleteNote.setBounds(13, 86, 163, 25);
		internalFrame_2.getContentPane().add(btnDeleteNote);

		JButton btnDeleteAttch = new JButton("Delete Attch");
		btnDeleteAttch.setBounds(12, 123, 164, 25);
		internalFrame_2.getContentPane().add(btnDeleteAttch);

		JButton btnChangeset = new JButton("Changeset");
		btnChangeset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					noteManager.changeset(user);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateNotesList();
			}
		});
		btnChangeset.setBounds(13, 160, 163, 25);
		internalFrame_2.getContentPane().add(btnChangeset);

		JButton btnStartup = new JButton("Startup");
		btnStartup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					noteManager.startup(user);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateNotesList();
			}
		});
		btnStartup.setBounds(13, 197, 163, 25);
		internalFrame_2.getContentPane().add(btnStartup);
		internalFrame_2.setVisible(true);

		noteList = new DefaultListModel<>();
		attachmentList = new DefaultListModel<>();

		try {
			noteManager.startup(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateNotesList();
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[] { 129, 0 };
		gridBagLayout_1.rowHeights = new int[] { 113, 0 };
		gridBagLayout_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		internalFrame_1.getContentPane().setLayout(gridBagLayout_1);

		JScrollPane scrollPane_4 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
		gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_4.gridx = 0;
		gbc_scrollPane_4.gridy = 0;
		internalFrame_1.getContentPane().add(scrollPane_4, gbc_scrollPane_4);

		noteListGui = new JList<>();
		scrollPane_4.setViewportView(noteListGui);
		noteListGui.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				// Note abc = (Note)evt.getNewValue();
				// editorPane.setText(abc.getContent());
			}
		});
		noteListGui.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// Note theNote = noteList.get(list.getSelectedValue());
				// editorPane.setText(theNote.getContent());
				if (noteListGui.getSelectedValue() != null) {
					editorPane.setText("");
					attachmentList.clear();
					Note abc = noteListGui.getSelectedValue();
					editorPane.setText(abc.getContent());
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					rawNoteGui.setText(gson.toJson(abc));
					renderedNoteGui.setText(abc.getContent());
					if (!e.getValueIsAdjusting()) {
						if (abc.getAttachments() != null
								&& !(abc.getAttachments().isEmpty())) {
							for (Attachment attch : abc.getAttachments()) {
								attachmentList.addElement(attch);
							}
						}
					}
				}
			}
		});
		noteListGui.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getList().setModel(noteList);

		JInternalFrame internalFrame_3 = new JInternalFrame("Attachments List");
		internalFrame_3.setIconifiable(true);
		internalFrame_3.setMaximizable(true);
		internalFrame_3.setResizable(true);
		internalFrame_3.setBounds(12, 276, 200, 235);
		desktopPane.add(internalFrame_3);
		GridBagLayout gridBagLayout_2 = new GridBagLayout();
		gridBagLayout_2.columnWidths = new int[] { 128, 0 };
		gridBagLayout_2.rowHeights = new int[] { 102, 0 };
		gridBagLayout_2.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout_2.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		internalFrame_3.getContentPane().setLayout(gridBagLayout_2);

		JScrollPane scrollPane_5 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_5 = new GridBagConstraints();
		gbc_scrollPane_5.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_5.gridx = 0;
		gbc_scrollPane_5.gridy = 0;
		internalFrame_3.getContentPane().add(scrollPane_5, gbc_scrollPane_5);

		attachmnentListGui = new JList<>();
		scrollPane_5.setViewportView(attachmnentListGui);
		attachmnentListGui
				.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							BufferedImage bi;
							try {
								if (attachmnentListGui.getSelectedValue() != null) {
									bi = noteManager.retriveAttachment(user,
											attachmnentListGui
													.getSelectedValue()
													.getAttachmentId());
									ImageIcon icon = new ImageIcon(bi);
									attachViewer.setIcon(icon);
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
		attachmnentListGui.setModel(attachmentList);
		internalFrame_3.setVisible(true);
		frmNoteEditor.setVisible(true);
	}

	public void updateNotesList() {
		noteList.clear();
		Set<String> noteBookList = noteManager.getNotebookList(user);

		for (String noteBookStr : noteBookList) {
			Set<String> noteListStr = noteManager.getNotesList(user,
					noteBookStr);
			for (String noteStr : noteListStr) {
				Note theNote = noteManager.getNote(user, noteStr, noteBookStr);
				noteList.addElement(theNote);
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNoteEditor = new JFrame();
		frmNoteEditor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNoteEditor.setTitle("Note Editor");
		frmNoteEditor.setBounds(100, 100, 950, 550);
		GridBagLayout gridBagLayout_2 = new GridBagLayout();
		gridBagLayout_2.columnWidths = new int[] { 796, 0 };
		gridBagLayout_2.rowHeights = new int[] { 523, 0 };
		gridBagLayout_2.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout_2.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frmNoteEditor.getContentPane().setLayout(gridBagLayout_2);
	}

	public JList<Note> getList() {
		return noteListGui;
	}
}
