/*
��������ڴ�����дʵ��е���йؼ��ֶ��󣬳�Ա������дʣ�ǿ�Ⱥͼ���

*/
package dict;

public class SeWord {
	private String word;   //��д�
	private int id;        //��Excel�в����
	private int strength;  //ǿ�ȣ����ǿ�ȷ�Ϊ1,3,5,7,9�嵵��9��ʾǿ�����1Ϊǿ����С
	private int polar;      //���ԣ�1������壬2������壬3������а�������
	public SeWord(String word, int id, int strength, int polar) {
		super();
		this.word = word;
		this.id = id;
		this.strength = strength;
		this.polar = polar;
	}
	public String getWord() {
		return word;
	}
	public int getId() {
		return id;
	}
	public int getStrength() {
		return strength;
	}
	public int getPolar() {
		return polar;
	}
	public String toString(){
		return word+" "+strength+" "+polar;
	}
}