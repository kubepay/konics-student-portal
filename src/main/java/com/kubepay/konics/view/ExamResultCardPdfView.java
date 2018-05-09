package com.kubepay.konics.view;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

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
import com.kubepay.konics.config.AbstractPdfView;
import com.kubepay.konics.model.ExamResultCard;
import com.kubepay.konics.model.ExamResultCardMarks;

@Component
public class ExamResultCardPdfView extends AbstractPdfView {

  public static final String FONT = "static/fonts/OpenSans-Regular.ttf";
  public static final String FONTB = "static/fonts/OpenSans-Bold.ttf";
  
  private static final String img = "https://konics-portal.herokuapp.com/img/logo-konic.png";

  protected Font font10;
  protected Font font10b;
  protected Font font12;
  protected Font font12b;
  protected Font font14;
  protected Font font14b;

  public ExamResultCardPdfView() throws DocumentException, IOException {

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

    final ExamResultCard examResultCard = (ExamResultCard) model.get("examResultCard");

    if (examResultCard.isErrorOccured()) {
      document.add(new Paragraph("Error" + examResultCard.getErrorDescription()));
    } else {
      Image image = Image.getInstance(new URL(img));
      image.scaleToFit(100f, 100f);
      image.setAbsolutePosition(50f, 750f);
      document.add(image);
      Paragraph p;
      document.add(new Paragraph(" ", font10));
      document.add(new Paragraph(" ", font10));
      document.add(new Paragraph(" ", font10));
      p = new Paragraph("Learning Center: " + " " + examResultCard.getCenter(), font14);
      p.setAlignment(Element.ALIGN_RIGHT);
      document.add(p);
      document.add(new Paragraph(" ", font10));
      
      PdfPTable table = new PdfPTable(2);
      table.setWidthPercentage(100);
      
      PdfPCell seller = new PdfPCell();
      seller.setBorder(PdfPCell.NO_BORDER);
      seller.addElement(new Paragraph("Test Name: " + examResultCard.getName(), font12b));
      seller.addElement(new Paragraph("Subject: " + examResultCard.getSubject(), font12));
      seller.addElement(new Paragraph("Total Marks: " + examResultCard.getTotalMarks(), font12));
      seller.addElement(new Paragraph("Passing marks: " + examResultCard.getPassingMarks(), font12));
      seller.addElement(new Paragraph("Date of Conduct: " + examResultCard.getStrdtConduct(), font12));
      table.addCell(seller);
      
      PdfPCell buyer = new PdfPCell();
      buyer.setBorder(PdfPCell.NO_BORDER);
      seller.addElement(new Paragraph("Prepared By: " + examResultCard.getPreparedBy(), font12));
      seller.addElement(new Paragraph("Evaluated By: " + examResultCard.getEvaluatedBy(), font12));
      buyer.addElement(new Paragraph("Course: " + examResultCard.getCourseName(), font12b));
      buyer.addElement(new Paragraph("Section: " + examResultCard.getSection(), font12));
      buyer.addElement(new Paragraph("Stream: " + examResultCard.getStream(), font12));
      buyer.addElement(new Paragraph("Period: " + examResultCard.getSessionYear(), font12));
      buyer.addElement(new Paragraph("Date of Result: " + examResultCard.getStrdtResult(), font12));
      table.addCell(buyer);

      document.add(table);
      document.add(new Paragraph(" ", font10));
      p = new Paragraph("Result", font14b);
      p.setAlignment(Element.ALIGN_CENTER);
      document.add(p);
      document.add(new Paragraph(" ", font10));
      document.add(new Paragraph(" ", font10));
      
      table = new PdfPTable(3);
      table.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.setWidthPercentage(80);
      table.setSpacingBefore(10);
      table.setSpacingAfter(10);
      table.setWidths(new int[] { 6, 4, 4});
      table.addCell(getCell("Name:", Element.ALIGN_LEFT, font12b));
      table.addCell(getCell("Roll Number :", Element.ALIGN_LEFT, font12b));
      table.addCell(getCell("Marks Scored:", Element.ALIGN_LEFT, font12b));
      final List<ExamResultCardMarks> marks = examResultCard.getExamResultCardMarks();
      for (ExamResultCardMarks mark : marks) {
        table.addCell(getCell(mark.getStudentName(), Element.ALIGN_LEFT, font12));
        table.addCell(getCell(mark.getRollNumber(), Element.ALIGN_LEFT, font12));
        table.addCell(getCell(mark.getMarksObtained(), Element.ALIGN_RIGHT, font12));
      }
      document.add(table);
    }
    response.setHeader("Content-Disposition", "attachment; filename=\"result" + new Date().getTime() + ".pdf\"");
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
