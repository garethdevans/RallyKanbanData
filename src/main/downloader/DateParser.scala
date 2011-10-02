package downloader

import java.util.Date
import java.text.{SimpleDateFormat,DateFormat};

class DateParser{
	def parse(creationDate:String):Date = {
		new SimpleDateFormat("yyyy-MM-dd").parse(creationDate.split('T')(0))
	}
}
