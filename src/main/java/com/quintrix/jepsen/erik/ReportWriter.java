package com.quintrix.jepsen.erik;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.filechooser.FileSystemView;

/**
 * Writes the grade report to a file and moves the file into the current user's Documents folder.
 * @author Erik Jepsen
 *
 */
public class ReportWriter{
	/** The temporary file the report is written to. */
	private Path tempFile;
	/** The location the report will eventually be moved to. */
	private Path reportLoc;
	/** The location of the backup file in case a report already exists. */
	private Path backupLoc;
	/**
	 * The class constructor.
	 */
	public ReportWriter() {
		FileSystemView view = FileSystemView.getFileSystemView();
		reportLoc = Paths.get(view.getDefaultDirectory().getAbsolutePath(), "grades.txt");
		backupLoc = Paths.get(view.getDefaultDirectory().getAbsolutePath(), "grades.bak");
	}
	/**
	 * Creates a temporary file for writing a grade report to.
	 */
	public void CreateReport(){
		try {
			tempFile = Files.createTempFile("GradeReportTemp", ".txt");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	/**
	 * Writes the passed line to the report. Empty and null lines are ignored.
	 * @param thisLine The line to be written to the report.
	 * @throws RuntimeException Thrown if {@code CreateReport} has not been called or if the report has already been closed by {@code CloseReport}.
	 * @throws IOException Thrown if the report has not been started or cannot be written to.
	 */
	public void WriteLine(String thisLine) throws RuntimeException, IOException {
		if (tempFile == null) throw new RuntimeException("No temp file for the report has been created. \"CreateReport()\" must run before this method is called.");
		if (Files.notExists(tempFile)) throw new RuntimeException("The report has already been closed.");
		if (!Files.isWritable(tempFile)) throw new IOException("Unable to write to the temp file!");
		if (thisLine == null || thisLine.isEmpty()) return;
		try {
			Files.write(tempFile,
					thisLine.getBytes(),
					StandardOpenOption.APPEND);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	/**
	 * If a file exists in {@code backupLoc} it is deleted.<br>
	 * If a file exists in {@code reportLoc} it is moved to {@code backupLoc}.<br>
	 * Finally, the temporary report file is moved to {@code reportLoc}.
	 * @throws IOException Thrown if any of the delete or move operations encounter an I/O exception.
	 */
	public void CloseReport() throws IOException {
		if (tempFile == null || Files.notExists(tempFile)) throw new IOException("The temp file hasn't been opened.");
		if (Files.exists(reportLoc)) {
			if (Files.exists(backupLoc)) Files.delete(backupLoc);
			Files.move(reportLoc, backupLoc);
		}
		Files.move(tempFile, reportLoc);
	}
	/**
	 * Get the location {@code CloseReport} moves the report to.
	 * @return The absolute location the report will be moved to when closed.
	 */
	public String getOutputLoc() {
		return reportLoc.toString();
	}
	protected void finalize() throws Throwable{
		Files.deleteIfExists(tempFile);
		super.finalize();
	}
}
