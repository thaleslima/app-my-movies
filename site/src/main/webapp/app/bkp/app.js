var id;
//var urlws = "http://192.168.0.102:8080/ws-filme/services/";
var urlws = "http://localhost:8080/ws-filme/services/";
	
$(document).ready(function(){
  carregarFilmes();	
  $("#searchinput1").keyup(function() {
	  carregarFilmes();  
  });
   
  $(".opcoes-ul").hide();
  $("#mostra-opcoes").click(function(){
	    $(".opcoes-ul").toggle("linear");
  });
  
  $("input:radio[name=visto],input:radio[name=ordenar],'input:radio[name=agrupar]").bind( "change", function(event, ui) {
	  carregarFilmes();	
  });
});


function carregaresumo()
{
	$("#visto").on("slidestop", function(event, ui) {
		atualizarVisto();
	});
	
	$.getJSON(urlws +'getFilme/'+id, function(data) {
		$("#titulo-filme").html(data.titulo);
		$("#ano").html(data.ano);
		$("#audiooriginal").html(data.audioOriginal);
		$("#genero").html(data.genero.descricao);
		$("#tipoaudio").html(data.tipoAudio);
		$("#sinopse").html(data.sinopse);
		$("#visto").val(data.visto);
		$('#visto').slider('refresh');
	});
}

function atualizarVisto()
{
	$.ajax({
        type: "GET",
        url: urlws +"atualizaVisto/" + id +"/" + $("#visto").val(),
        success: function(data){
            //alert('Wine updated successfully');
            carregarFilmes($("#searchinput1").val());
        },
        error: function(){
            //alert('updateWine error: ' + textStatus);
        }
    });
}


function setid(a)
{
	id = a;
}

function carregarFilmes()
{
	var texto = $('#searchinput1').val();
	var visto = $('input:radio[name=visto]:checked').val();
	var ordenar = $('input:radio[name=ordenar]:checked').val();
	
	//var dataAtual = $.now();
	
	//alert(dataAtual);
	
	//dataAtual = dataAtual - localStorage.horaupdate;
	
	//alert(dataAtual);
	
	//jQuery.ajax({
   //     url: urlws +'retornaTodos/X/ID',
        //dataType: "json", //Tipo de Retorno
   //     success: function(json){
   //        localStorage.filmes=JSON.stringify(json);
   //        localStorage.horaupdate=$.now();
    //    },
    //    error:function()
    //    {
        	//localStorage.horaupdate=$.now();
        	//1361317194030
        	//1361317234551
        	//1361317849292
        	//1361320823772
        	//      3612269
     //   }
     //});
	
	$.getJSON(urlws +'retornaTodos/'+visto+'/'+ordenar+'/'+texto, function(data) {
		carregaFilmes(data);
	//}).error(function() { carregaFilmes(JSON.parse(localStorage.filmes)); });
	}).error(function() {  });
}


function carregaFilmes(data)
{
	var ordenar = $('input:radio[name=ordenar]:checked').val();
	var agrupar = $('input:radio[name=agrupar]:checked').val();
	var genero = "";
	
	list = "";
	$.each(data, function(i,row){
		var sTitulo = row.titulo;
		
		if(ordenar == "genero" && agrupar == "S")
		{
			if(genero != row.genero.descricao)
			{
				list += '<li data-role="list-divider" role="heading">';
				list += row.genero.descricao;
				list += '	</li>';
				genero = row.genero.descricao;
			}
		}
		
		if(row.visto == "S")
		{
			list += '<li data-theme="b">';
		}
		else
		{
			list += '<li data-theme="c">';
		}
		list += '<a href="resumo.html" data-transition="slide" onclick="setid('+ row.id +');">'+sTitulo+'</a></li>';
	});
	
	if(ordenar == "id" || agrupar == "N" || ordenar == "genero")
	{
		$("#listview2").hide();
		$("#listview").show();
		$_listview = $("#listview");
	}
	else
	{
		$("#listview").hide();
		$("#listview2").show();
		$_listview = $("#listview2");
	}
	
	$_listview.html(list);	
	$_listview.listview('refresh');
}


function carregarDados(texto)
{
	var list;
	var $_listview;
	
	$.ajax({
        type: "GET",
        url: "Filmes.xml",
        dataType: "xml",
        success: function (xml) {
        	
        	list = "";
        	
            $(xml).find('Filme').each(function () {
                var sTitulo = $(this).find('Titulo').text();
                
                if(row.visto == "S")
        		{
        			list += '		<li data-theme="b">';
        		}
        		else
        		{
        			list += '		<li data-theme="c">';
        		}
                
        		list += '		<a href="#" data-transition="slide">';
        		list += '		' + sTitulo + texto;
        		list += '		</a>';
        		list += '		</li>';
            });
            
            $_listview = $("#listview");
            $_listview.html(list);	
    		$_listview.listview('refresh');
        },
        error: function () {
            alert("Ocorreu um erro inesperado durante o processamento.");
        }
    });
}



