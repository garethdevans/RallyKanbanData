package downloader

import java.io.File

object Runner {
  def main(args : Array[String]) : Unit = {
    println(new File(".").getAbsolutePath())
    
	new ProjectBuilder().buildAll foreach{(project) =>
		project.toCsv
	}
  }
}

// https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement?query(project = https://rally1.rallydev.com/slm/webservice/1.27/project/3684505712)