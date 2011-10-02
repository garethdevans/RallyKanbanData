package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import java.text.{SimpleDateFormat,DateFormat};

class DateParserTest extends FlatSpec with ShouldMatchers {
	val dateParser = new DateParser

	"A rally date string" should "parse to a date type" in {
		DateFormat.getDateInstance(DateFormat.SHORT).format(dateParser.parse("2011-09-18T23:51:35.724Z")) == "18/9/2011"
		DateFormat.getDateInstance(DateFormat.SHORT).format(dateParser.parse("2011-07-10T22:54:10.489Z")) == "10/7/2011"
	}
	
}
