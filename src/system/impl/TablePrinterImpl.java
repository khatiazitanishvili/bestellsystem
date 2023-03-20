package system.impl;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import system.TablePrinter;




class TablePrinterImpl implements TablePrinter {
	final List<Column> columns = new ArrayList<Column>();
	final String rowSpec;	// default row spec:  "| | | |"
	final String lineSpec;	// default line spec: "+-+-+-+"
	final StringBuffer sb;
	final static char SPACE = 0x20;
	final static char NUL = 0x00;
	final static char L = 'L';
	final static char R = 'R';
	enum ALIGN {L, R};			// left, right column alignment

	class Column {
		final boolean lb, rb;	// has left, right column border
		final char fill;		// fill character, default is SPACE
		final ALIGN align;		// column alignment
		final int width;		// column width
		//
		Column(Column prev, String spec, int width) {
			int len = spec != null? spec.length() : -1;
			boolean prevColHasRightBorder = prev != null && prev.rb;
			this.lb = ! prevColHasRightBorder && len > 0 && spec.charAt(0)=='|';
			int fi = len > 0 && spec.charAt(0)=='|'? 1 : 0;
			char cf = len > fi? spec.charAt(fi) : NUL;
			this.fill = cf < SPACE || cf==L||cf==R||cf=='|'? SPACE : cf;
			char cl = len > 0? spec.charAt(len-1) : NUL;
			this.align = cl==R? ALIGN.R : ALIGN.L;	// left-aligned column default
			this.rb = cl!=L && cl!=R? len>1 && cl=='|'
					: (len > 2? spec.charAt(len-2) : NUL)=='|';
			this.width = width > 0? width - (lb? 1 : 0) - (rb? 1 : 0) : 0;
		}
	}

	TablePrinterImpl(StringBuffer sb, Consumer<Builder> builder) {
		this.sb = sb==null? new StringBuffer() : sb;
		builder.accept(new Builder() {
			@Override
			public Builder column(String spec, int width) {
				Column prev = columns.size() > 0? columns.get(columns.size()-1) : null;
				columns.add(new Column(prev, spec, width));
				return this;
			}
		});
		this.rowSpec = "| ".repeat(columns.size()) + "|";
		this.lineSpec = "+-".repeat(columns.size()) + "+";
	}

	@Override
	public TablePrinter line() { return render(lineSpec); }

	@Override
	public TablePrinter line(String spec) { return render(spec); }

	@Override
	public TablePrinter row(String... args) {
		int lena = args != null? args.length : -1;
		String arg0 = lena > 0? args[0] : "";
		boolean hasSpec = arg0.startsWith("@");	// shift args[] << 1
		String[] args2 = hasSpec? Arrays.copyOfRange(args, 1, lena) : args;
		return render(hasSpec? arg0 : rowSpec, args2);
	}

	@Override
	public void print(PrintStream ps) { ps.print(sb); }

	private TablePrinter render(String spec, String... args) {
		int lens = spec != null? spec.length() : -1;
		int lena = args != null? args.length : -1;
		spec = lens > 0 && spec.startsWith("@")? spec.substring(1) : spec;
		// IntStream.range(0, columns.size()).forEach( i -> {
		for(int i=0; i < columns.size() && i < lens/2; i++) {
			Column col = columns.get(i);
			int j = i*2;
			if(col.lb && j < lens) {
				sb.append(String.valueOf(spec.charAt(j)));
			}
			if(++j < lens || col.fill != SPACE) {
				String text = i < lena && args[i] != null? args[i] : "";
				int d = col.width - text.length();
				if(d > 0) {	// fill to width from left or right
					char fc = spec==rowSpec && col.fill != SPACE? col.fill : NUL;
					fc = fc==NUL && i < lens? spec.charAt(j) : fc;
					String fill = String.valueOf(fc).repeat(d);
					boolean left = col.align==ALIGN.L;
					sb.append(left? text : fill).append(left? fill : text);
				}
				if(d < 0) {	// cut to width
					sb.append(col.align==ALIGN.R? text.substring(-d)// cut from left
						: text.substring(0, text.length() + d));	// cut from right
				}
				if(d==0) {
					sb.append(text);
				}
			}
			if(col.rb && ++j < lens) {
				sb.append(String.valueOf(spec.charAt(j)));
			}
		};
		sb.append("\n");
		return this;
	}
};