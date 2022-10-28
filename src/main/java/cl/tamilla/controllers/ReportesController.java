package cl.tamilla.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import cl.tamilla.modelos.ProductoModel;
import cl.tamilla.servicice.ProductoService;
import cl.tamilla.utilidades.Utilidades;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

@Controller
@RequestMapping("/reportes")
public class ReportesController {   

    @Value("${jose.valores.base_url_upload}")
    private String base_url_upload;

    
    private final TemplateEngine templateEngine; // PDF - Para quitar este error,, no puede ser nulo. por eso se tiene que generar un constructor y asignarle algun valor
    private XSSFWorkbook workbook; // EXCEL

    public ReportesController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine; // PDF
        this.workbook = new XSSFWorkbook(); //EXCEL
    }
    
    @GetMapping("")
    public String home(){
        return "reportes/home";
    }
    
    /////////// Rreportes con PDF /////////// usando la libreria ITEXT pagina: itextpdf.com.. se debe instalar en el pom

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/pdf")
    public ResponseEntity<?> productos_pdf(HttpServletRequest request, HttpServletResponse response){
        WebContext context = new WebContext(request, response, servletContext);
		context.setVariable("titulo", "PDF Dinámico desde Spring Boot"); //Es como si pasaramos los parametros con model.addAtribute
		context.setVariable("ruta", this.base_url_upload);
		
		String html = this.templateEngine.process("reportes/pdf", context); //primer atributo lugar donde se encuentra la vista
		
		ByteArrayOutputStream target = new ByteArrayOutputStream();
		
		ConverterProperties converterProperties =new ConverterProperties();
        converterProperties.setBaseUri("http://192.168.1.9:8080"); //IP

        HtmlConverter.convertToPdf(html, target, converterProperties);
		
		byte[] bytes = target.toByteArray();
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytes);
    }

    
    //////////////////////////////// Excelen libreria Apache Poi ////////////////////////////////

    @Autowired
    private ProductoService prosuctoService;

    private XSSFSheet sheet;

    @GetMapping("/excel")
    public void excel (HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream"); //Esto es el cabecero para construir el excel
        Long time = System.currentTimeMillis(); //Asignacion de nombre dinamico
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=reporte_" + time + ".xlsx";

        response.setHeader(headerKey, headerValue);
        response.setHeader("time", time+""); //el segundo parametro lo pasamos con las "" para que lo tome como un string..  y no tener que parsearlo

        this.sheet = this.workbook.createSheet("Hola 1"+time); // nombre de las hojas del excel
        CellStyle style = this.workbook.createCellStyle();

        XSSFFont font = this.workbook.createFont();
        //font.setBold(true); //negrilla si no mal recuerdo
        //font.setFontHeight(16);
        style.setFont(font);

        //Generamos las columnas
        Row row = this.sheet.createRow(0);
        createCell(row, 0, "id", style);
        createCell(row, 1, "nombre", style);
        createCell(row, 2, "Descripcion", style);
        createCell(row, 3, "Precio", style);
        createCell(row, 4, "Foto", style);
        createCell(row, 5, "Time", style);

        //Generamos las filas dinamicas del reporte
        List<ProductoModel> datos = this.prosuctoService.listar2();
        int rowCount = 1;
        for (ProductoModel dato: datos) {
            row = this.sheet.createRow(rowCount++);
            int columnaCount = 0;
            createCell(row, columnaCount++, dato.getId(), style);
            createCell(row, columnaCount++, dato.getNombre(), style);
            createCell(row, columnaCount++, dato.getDescripcion(), style);
            createCell(row, columnaCount++, Utilidades.numberFormat(dato.getPrecio()), style); //VALOR FORMATEADO
            createCell(row, columnaCount++, this.base_url_upload+dato.getFoto(), style);
            createCell(row, columnaCount++, time+"", style);
        }

        //Se formatea la salida ..
        ServletOutputStream outputStream = response.getOutputStream();
        this.workbook.write(outputStream);
        this.workbook.close();
        outputStream.close();
    }

    private void createCell(Row row, int columCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columCount);
        Cell cell = row.createCell(columCount);
        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if ( value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    //////////////////  CSV  //////////////////
    /*@GetMapping("/csv")
    public void csv(HttpServletResponse response) throws IOException{
        
        response.setContentType("text/csv"); //Esto es el cabecero para construir el excel
        Long time = System.currentTimeMillis(); //Asignacion de nombre dinamico
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=reporte_" + time + ".csv";
        response.setContentType("text/csv;charset=utf-8");
        
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Nombre","Descripcion", "Precio", "Foto"};
        String[] nameMapping = {"id", "nombre","descripcion", "precio", "foto"}; //Este es para hacer el mapero

        csvWriter.writeHeader(csvHeader);

        //Formateamos las filas dinamicas
        List<ProductoModel> datos = this.prosuctoService.listar2();
        for (ProductoModel dato: datos) {
            csvWriter.write(dato, nameMapping);
        }

        csvWriter.close();
    }*/

    @GetMapping("/csv")
	public void csv(HttpServletResponse response) throws IOException 
	{
		response.setContentType("text/csv");
		long time = System.currentTimeMillis();
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=reporte_" + time + ".csv";
		response.setContentType("text/csv;charset=utf-8");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"ID", "Nombre", "Descripción", "precio", "foto"};
		String[] nameMapping = {"id", "nombre", "descripcion", "precio", "foto"};
		
		csvWriter.writeHeader(csvHeader);
		
		//formateamos las filas dinámicas
		List<ProductoModel> datos = this.prosuctoService.listar2();
		for (ProductoModel dato : datos) 
		{
			csvWriter.write(dato, nameMapping);
		}
		//cerramos la comunicación
		csvWriter.close();
	}
    
}
