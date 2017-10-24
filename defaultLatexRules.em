/header/

\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage{graphicx}
\usepackage{hyperref}
\title{Interprete Extensible de Markdown}
\date{2013-09-01}
\author{Agustin Bauer, Alan Gonzalez}
\begin{document}
\maketitle

/header/
begin{

	BOLD -> * text * -> \textbf\{ text \}
	LABEL -> @ text @ -> \hypertarget\{ text \}\{\}
	ITAlIC -> $ text $ -> \textit\{ text \}
	UNDERLINE -> $$ text $$ -> \underline\{ text \}
	SECTION -> # text -> \section\{ text \}
	SECTION2 -> ## text -> \subsection\{ text \}
	SECTION3 -> ### text -> \subsubsection\{ text \}
	
	nested{
		LIST ->   text  ->  \\begin\{itemize\}_ text _\\end\{itemize\}
		ITEM -> + text -> \\item_ text 
	}
	
	LINK -> [ text ] ( literal ) -> \\href\{ literal \}\{ text \}	
	IMG -> ![ text ] ( literal ) -> \\includegraphics\{_ literal _ text _\}
	HYPERLINK -> !![ text ] ( literal ) -> \\hyperlink\{ literal \}\{ text \}	
	
}
	
/footer/

\end{document}

/footer/