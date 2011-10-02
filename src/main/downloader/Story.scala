package downloader

class Story(
	val id:String,
	val name:String,
    val uri:String, 
	val storyType:String, 
	val kanbanStates:List[KanbanData]){
}