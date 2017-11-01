/header/

\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage{graphicx}
\usepackage{hyperref}
\usepackage{listings}
\title{Interprete Extensible de Markdown}
\date{2013-09-01}
\author{Agustin Bauer, Alan Gonzalez}
\begin{document}
\maketitle

/header/
begin{

	BOLD -> * text * -> \textbf\{ text \}
	ITAlIC -> ** text ** -> \textit\{ text \}
	LABEL -> @ text @ -> \hypertarget\{ text \}\{\}
	UNDERLINE -> _ text _ -> \underline\{ text \}
	PARAPH -> $ text $ -> _ text \\par
	CODE -> ` text ` -> \begin\{lstlisting\} text \end\{lstlisting\}
	SECTION -> # text -> \section\{ text \}
	SECTION2 -> ## text -> \subsection\{ text \}
	SECTION3 -> ### text -> \subsubsection\{ text \}
	
	nested{
		LIST ->   text  ->  \\begin\{itemize\}_ text _\\end\{itemize\}
		ITEM -> + text -> \\item_ text 
	}
	
	nested{
		TABLE ->  text -> \\begin\{tabular\}\{l_\|_c_\|_r_\}_ text _\\end\{tabular\} 
		ROW  -> ¡ text -> _ text _\\\\
		COL -> - text - -> _ text _&
	}
	
	LINK -> [ text ] ( literal ) -> \\href\{ literal \}\{ text \}	
	IMG -> ![ text ] ( literal ) -> \\includegraphics\{_ literal _ text _\}
	HYPERLINK -> !![ text ] ( literal ) -> \\hyperlink\{ literal \}\{ text \}	
	
}
	
/footer/

\end{document}

/footer/