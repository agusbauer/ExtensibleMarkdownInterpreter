/header/

<!DOCTYPE html>
<html>
<head>
<title>Page Title</title>
<link rel="stylesheet" type="text/css" href="estilos.css">
</head>
<body>

/header/

begin{

	ITAlIC -> * text * -> <em> text </em>
	BOLD -> ** text ** -> <strong> text </strong>
	LABEL -> @ text @ -> <a_name=" text "></a>
	UNDERLINE -> _ text _ -> <u> text </u>
	PARAPH -> $ text $ -> <p> text </p>
	CODE ->  ` text ` -> <code> text </code>
	TITLE -> # text -> <h1> text </h1>
	TITLE2 -> ## text -> <h2> text </h2>
    TITLE3 -> ### text -> <h3> text </h3>
    TITLE4 -> #### text -> <h4> text </h4>
    
	nested{
		LIST -> text -> <ul> text </ul>
		ITEM -> + text -> <li> text </li>
	}
	
	nested{
		TABLE ->  text -> <table> text </table>
		ROW  -> ¡ text -> <tr> text </tr>
		COL -> - text - -> <td> text </td>
	}
	
	LINK -> [ text ] ( literal ) -> <a_href=" literal "> text </a>
	IMG -> ![ text ] ( literal ) -> <img_src=" literal "alt=" text ">
	HYPERLINK -> !![ text ] ( literal ) -> <a_href=" literal "> text </a>

}
	
/footer/

</body>
</html>

/footer/