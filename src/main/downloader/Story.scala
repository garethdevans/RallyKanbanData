package downloader

import java.util.Date

class Story(
	val id:String,
	val parentId:String,
	val name:String,
    val uri:String, 
	val storyType:String, 
	val release:String,
	val hasChildren:Boolean,
	val creationDate:Date,
	val kanbanStates:List[KanbanData]){
}