/**
 * 
 */
console.log("This is a script file");

const toggleSidebar = () =>{
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
		$("#menuicon").css("visibility","visible")
	}
	else{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
		$("#menuicon").css("visibility","hidden")
	}
};