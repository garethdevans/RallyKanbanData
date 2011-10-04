package downloader

import java.util.Date
import java.text.{SimpleDateFormat,DateFormat};

class DateParser{
	def parse(creationDate:String):Date = {
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(creationDate)
	}
}
