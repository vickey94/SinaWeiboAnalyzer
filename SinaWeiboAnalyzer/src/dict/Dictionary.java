/*��������ڴ�����дʵ�
�෽���еĲ�����һ����������дʵ�excel�ļ���ÿ����дʰ������ǿ�Ⱥͼ���
�෽���ķ���ֵ��Map���󣬱�����������дʣ��Դ���Ϊ�����԰������ǿ�Ⱥͼ��Ե�SeWord����Ϊֵ
*
*/

package dict;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Dictionary {
	
	public static Map getDictionary(String filename) throws IOException {
		Map<String, SeWord> dictionary = new HashMap<String, SeWord>();
		InputStream stream = new FileInputStream(filename);
		Workbook wb = new XSSFWorkbook(stream);
		Sheet sheet1 = wb.getSheetAt(0);
		int rowNums = sheet1.getPhysicalNumberOfRows();
		SeWord sw = null;
		for(int r = 0; r<rowNums; r++){
			Row row = sheet1.getRow(r);
			String w = row.getCell(0).getStringCellValue().trim();
			int s = (int) row.getCell(1).getNumericCellValue();  
			int p = (int) row.getCell(2).getNumericCellValue();  
			sw = new SeWord(w,r,s,p);     //SeWord(String word, int id, int strength, int polar)
			dictionary.put(w, sw);
		}
		wb.close();
		return dictionary;
	}
}