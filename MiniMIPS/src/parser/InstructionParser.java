package parser;

import constants.INSTRUCTIONS;
import dataObjects.Instruction;
import Helper.Validate;

//TODO if label already exists
public class InstructionParser implements IParser {

    private String tokens[];
    //private String input;

    private Instruction tempInstruction;
    //private INSTRUCTIONTYPES tempInstructionType;
    private INSTRUCTIONS tempInstructionName;
    private String tempLabel;
    private String tempV1;
    private String tempV2;
    private String tempV3;

    public InstructionParser() {
        Instruction.createMappings();
    }

    @Override
    public void parse(String input) {
        //this.input = input;

        clear();
        parseLabel(input);
        parseInstructionName();
        parseRegImm();
        addInstruction();
        System.out.println(Instruction.getInstructionList().toString());
        for (Instruction x : Instruction.getInstructionList()) {
        }
    }

    private void clear() {
        tempLabel = "";
        tempV1 = "";
        tempV2 = "";
        tempV3 = "";
    }

    /**
     * Gets the label from the input. tokens[0]
     *
     * @param input
     */
    private void parseLabel(String input) {
        tokens = input.split(":", 2);
        tempLabel = tokens[0];
    }

    /**
     * Gets the instructionName. If there is a label, tokens will get the split
     * tokens[1] Else, tokens will get the split tokens[0] - tokens[0] holds the
     * instruction name - tokens[1] holds the registers and/or immediate
     */
    private void parseInstructionName() {
        try {
            tokens[1] = tokens[1].trim();
            tokens = tokens[1].split(" ", 2);
        } catch (Exception e) {
            tempLabel = "";
            tokens[0] = tokens[0].trim();
            tokens = tokens[0].split(" ", 2);
        } finally {
            try {
                tempInstructionName = INSTRUCTIONS.valueOf(tokens[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                System.exit(0);
            }
        }
    }

    /**
     * Gets the registers and/or immediate.
     */
    private void parseRegImm() {

        tokens = tokens[1].trim().split("[;#]"); // removes comments

        switch (tempInstructionName) {
            case DADDU:
            case DMULT:
            case OR:
            case SLT:
            case DSLL: // RD,RS,SHF/IMM
            case BEQ: // RS,RT,IMM
            case ANDI:
            case DADDIU: // RT,RS,IMM
                tokens = tokens[0].trim().split(",");
                if (tempInstructionName.toString().matches("DMULT")) {
                    tempV1 = "0";
                } else {
                    tempV1 = tokens[0].trim();
                }
                /**
                 * Validation starts here.
                 */
                if (Validate.isRegister(tokens[1].trim())) {
                    tempV2 = tokens[1].trim();
                    System.out.println(tempV2);
                } else {
                    System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                    System.err.println("here at InstructionParser");
                    System.exit(0);
                }

                if (Validate.isRegister(tokens[2].trim())) {
                    tempV3 = tokens[2].trim();
                } else {
                    System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                    System.err.println("here at InstructionParser");
                    System.exit(0);
                }
                break;

            case ADDS:
            case MULS: // FD,FS,FT
                tokens = tokens[0].trim().split(",");
                /**
                 * Validation starts here.
                 */
                if (Validate.isFloatRegister(tokens[1].trim())) {
                    tempV2 = tokens[1].trim();
                } else {
                    System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                    System.out.println("here at InstructionParser");
                    System.exit(0);
                }

                if (Validate.isFloatRegister(tokens[2].trim())) {
                    tempV3 = tokens[2].trim();
                } else {
                    System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                    System.err.println("here at InstructionParser");
                    System.exit(0);
                }
                break;

            case LW:
            case LWU:
            case SW:
                tokens = tokens[0].trim().split(",");
                /**
                 * Validation starts here.
                 */
                if (Validate.isRegister(tokens[0].trim())) {
                    tempV1 = tokens[0].trim();
                } else {
                    System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                    System.err.println("here at InstructionParser");
                    System.exit(0);
                }
                tokens = tokens[1].trim().split("\\(");
                tempV2 = tokens[0].trim();
                System.out.println("v2: " + tempV2);
                if (Validate.isRegister(tokens[1].substring(0, tokens[1].length() - 1))) {
                    tempV3 = tokens[1].substring(0, tokens[1].length() - 1);
                    System.out.println("v3: " + tempV3);
                } else {
                    System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                    System.err.println("here at InstructionParser");
                    System.exit(0);

                }
                break;

            /**
             * Validation starts here.
             */
            case LS: // FDDDDDDDD,IMM(RSSSSSSSSSS)
            case SS: // FTTTTTTTT, IMM(RSSSSSSSSS)
                tokens = tokens[0].trim().split(",");

                if (Validate.isFloatRegister(tokens[0].trim())) {
                    tempV1 = tokens[0].trim();
                } else {
                    System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                    System.err.println("here at InstructionParser");
                    System.exit(0);
                }

                tokens = tokens[1].trim().split("\\(");
                tempV2 = tokens[0].trim();
                System.out.println("v2: " + tempV2);

                if (Validate.isRegister(tokens[1].substring(0, tokens[1].length() - 1))) {
                    tempV3 = tokens[1].substring(0, tokens[1].length() - 1);
                    System.out.println("v3: " + tempV3);
                } else {
                    System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Unknown instruction.");
                    System.err.println("here at InstructionParser");
                    System.exit(0);

                }
                break;

            case J: // Label
                tempV1 = tokens[0].trim();
        }
    }

    /**
     * If input is valid, adds the parsed instruction to the instruction list.
     */
    private void addInstruction() {
        if (isLabelValid()) {
            tempInstruction = new Instruction();
            tempInstruction.setInstructionName(tempInstructionName);
            tempInstruction.setLabel(tempLabel);
            tempInstruction.setV1(tempV1);
            tempInstruction.setV2(tempV2);
            tempInstruction.setV3(tempV3);
            Instruction.getInstructionList().add(tempInstruction);
        } else {
            System.err.println("[ERROR at line:" + FileParser.getLineCtr() + "] Label already exits.");
            System.exit(0);
        }
    }

    /**
     * Checks whether the label is already existing.
     *
     * @return TRUE if label is valid, FALSE if label is already in use.
     */
    private boolean isLabelValid() {
        for (int i = 0; i < Instruction.getInstructionList().size(); i++) {
            if (!tempLabel.isEmpty()) {
                if (Instruction.getInstructionList().get(i).getLabel().matches(tempLabel)) {
                    return false;
                }
            }
        }

        return true;
    }
}
