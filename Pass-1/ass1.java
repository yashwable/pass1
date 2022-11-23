import java.util.*;
import java.io.*;

class Tuple {
	String mnemonic, bin_opcode;

	Tuple() {
	}

	Tuple(String s1, String s2) {
		mnemonic = s1;
		bin_opcode = s2;
	}
}

class ass1 {
	static int lc;
	static List<Tuple> mot;

	static PrintWriter out_pass1;
	static int line_no;

	public static void main(String args[]) throws Exception {
		initializeTables();
		System.out.println("====== PASS 1 ======\n");
		pass1();

	}

	static void initializeTables() throws Exception {

		mot = new LinkedList<>();

		String s;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(new FileInputStream("mot.txt")));
		while ((s = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(s, "	", false);
			mot.add(new Tuple(st.nextToken(), st.nextToken()));
		}
		for (Tuple y : mot) {
			System.out.println("mnemonic    " + y.mnemonic);
			System.out.println("opcode      " + y.bin_opcode);
		}

	}

	static void pass1() throws Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));
		out_pass1 = new PrintWriter(new FileWriter("output_pass1.txt"), true);
		PrintWriter out_symtable = new PrintWriter(new FileWriter("out_symtable.txt"), true);

		String s, output, op;
		op = " ";
		output = " ";
		int i;
		while ((s = input.readLine()) != null) {

			StringTokenizer st = new StringTokenizer(s, "	", false);
			String s_arr[] = new String[st.countTokens()];
			for (i = 0; i < s_arr.length; i++) {
				s_arr[i] = st.nextToken();
			}

			if (s_arr.length == 3) {
				System.out.println("label =" + s_arr[0]);
				i = 1;

			}

			for (Tuple y : mot) {
				if (s_arr[i].equalsIgnoreCase(y.mnemonic)) {
					op = y.bin_opcode;
					break;
				}

			}
			if (op.startsWith("(IS")) {

				output = lc + "	" + op + "	" + s_arr[i + 1];
				lc = lc + 1;
			} else if (s_arr[i].equalsIgnoreCase("start") || s_arr[i].equalsIgnoreCase("origin")) {
				String ne = "(C," + s_arr[i + 1] + ")";
				output = lc + "	" + op + "	" + ne;
				lc = Integer.parseInt(s_arr[i + 1]);
			} else if (s_arr[i].equalsIgnoreCase("ds")) {
				String ne = "(C," + s_arr[i + 1] + ")";
				output = lc + "	" + op + "	" + ne;
				lc = lc + Integer.parseInt(s_arr[i + 1]);
			}

			out_pass1.println(output);
		}
	}
}
