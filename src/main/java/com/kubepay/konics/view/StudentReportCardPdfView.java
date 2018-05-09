package com.kubepay.konics.view;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.kubepay.konics.config.AbstractPdfView;
import com.kubepay.konics.model.StudentReportCard;
import com.kubepay.konics.model.StudentReportCardMarks;

public class StudentReportCardPdfView extends AbstractPdfView {

  public static final String FONT = "static/fonts/OpenSans-Regular.ttf";
  public static final String FONTB = "static/fonts/OpenSans-Bold.ttf";
  public static final String IMG = "static/img/logo-konic.png";

  protected Font font10;
  protected Font font10b;
  protected Font font12;
  protected Font font12b;
  protected Font font14;
  protected Font font14b;

  public StudentReportCardPdfView() throws DocumentException, IOException {

    BaseFont bf = BaseFont.createFont(FONT, BaseFont.CP1252, BaseFont.EMBEDDED);
    BaseFont bfb = BaseFont.createFont(FONTB, BaseFont.CP1252, BaseFont.EMBEDDED);
    font10 = new Font(bf, 10);
    font10b = new Font(bfb, 10);
    font12 = new Font(bf, 12);
    font12b = new Font(bfb, 12);
    font14 = new Font(bf, 14);
    font14b = new Font(bfb, 14);
  }

  @Override
  protected void buildPdfDocument(final Map<String, Object> model, final Document document, final PdfWriter writer,
      final HttpServletRequest request, final HttpServletResponse response) throws Exception {

    final StudentReportCard studentReportCard = (StudentReportCard) model.get("studentReportCard");

    if (studentReportCard.isErrorOccured()) {
      document.add(new Paragraph("Error" + studentReportCard.getErrorDescription()));
    } else {
      Image image = Image.getInstance(getImagePath(IMG));
      image.scaleToFit(100f, 100f);
      image.setAbsolutePosition(50f, 750f);
      document.add(image);
      Paragraph p;
      document.add(new Paragraph(" ", font10));
      document.add(new Paragraph(" ", font10));
      document.add(new Paragraph(" ", font10));
      p = new Paragraph("Learning Center: " + " " + studentReportCard.getCenter(), font14);
      p.setAlignment(Element.ALIGN_RIGHT);
      document.add(p);
      document.add(new Paragraph(" ", font10));
      
      PdfPTable table = new PdfPTable(2);
      table.setWidthPercentage(100);
      
      PdfPCell seller = new PdfPCell();
      seller.setBorder(PdfPCell.NO_BORDER);
      seller.addElement(new Paragraph("Name: " + studentReportCard.getName(), font12b));
      seller.addElement(new Paragraph("Roll No.: " + studentReportCard.getRollNumber(), font12));
      seller.addElement(new Paragraph(
          "Attendance: " + studentReportCard.getStudentAttendance() + "/" + studentReportCard.getBatchAttendance(),
          font12));
      table.addCell(seller);
      
      PdfPCell buyer = new PdfPCell();
      buyer.setBorder(PdfPCell.NO_BORDER);
      buyer.addElement(new Paragraph("Course: " + studentReportCard.getCourseName(), font12b));
      buyer.addElement(new Paragraph("Section: " + studentReportCard.getSection(), font12));
      buyer.addElement(new Paragraph(studentReportCard.getStream() + " " + studentReportCard.getSessionYear(), font12));
      table.addCell(buyer);

      document.add(table);
      document.add(new Paragraph(" ", font10));
      p = new Paragraph("Report Card", font14b);
      p.setAlignment(Element.ALIGN_CENTER);
      document.add(p);
      document.add(new Paragraph(" ", font10));
      document.add(new Paragraph(" ", font10));
      
      table = new PdfPTable(4);
      table.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.setWidthPercentage(80);
      table.setSpacingBefore(10);
      table.setSpacingAfter(10);
      table.setWidths(new int[] { 5, 3, 3, 3 });
      table.addCell(getCell("Test:", Element.ALIGN_LEFT, font12b));
      table.addCell(getCell("Subject :", Element.ALIGN_LEFT, font12b));
      table.addCell(getCell("Marks Scored:", Element.ALIGN_LEFT, font12b));
      table.addCell(getCell("Total Marks:", Element.ALIGN_LEFT, font12b));
      final List<StudentReportCardMarks> marks = studentReportCard.getStudentReportCardMarks();
      for (StudentReportCardMarks mark : marks) {
        table.addCell(getCell(mark.getTestName(), Element.ALIGN_LEFT, font12));
        table.addCell(getCell(mark.getSubject(), Element.ALIGN_LEFT, font12));
        table.addCell(getCell(mark.getMarksObtained(), Element.ALIGN_RIGHT, font12));
        table.addCell(getCell(mark.getTotalMarks(), Element.ALIGN_RIGHT, font12));
      }
      document.add(table);
    }
    response.setHeader("Content-Disposition", "attachment; filename=\"reportcard" + new Date().getTime() + ".pdf\"");
  }
  
  private String getImagePath(final String img) {
    
    //final ClassLoader classLoader = getClass().getClassLoader();
    return "/" 
    + getClass().getProtectionDomain().getCodeSource().getLocation().getPath()  
    + img;
    
    //InputStream is = classLoader.getResourceAsStream(img);
    //final File file = new File(classLoader.getResourceAsStream(img),)
    //return classLoader.getResource(img).getPath();
  }

  private PdfPCell getCell(final String value, final int alignment, final Font font) {

    PdfPCell cell = new PdfPCell();
    cell.setUseAscender(true);
    cell.setUseDescender(true);
    Paragraph p = new Paragraph(value, font);
    p.setAlignment(alignment);
    cell.addElement(p);
    return cell;
  }

}
