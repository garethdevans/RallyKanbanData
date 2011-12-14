package downloader

import java.io.File

object Runner {
  def main(args : Array[String]) : Unit = {
    println(new File(".").getAbsolutePath())
    
	new ProjectBuilder().output
  }
}