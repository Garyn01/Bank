package application;

import lombok.Data;

public @Data class Account {
    private String name;
    private String surname;
    private Integer PESEL;
    private String city;
    private String road; //TODO might want to add a building number
    private Double money; //FIXME damn dp is ',' and sometimes goes crazy, maybe BigDecimal is the way to go?

    public Account(){
        name=null;
        surname=null;
        PESEL=-1;
        city=null;
        road=null;
        money=-1.0;
    }
    public Account(String name_,String surname_, Integer PESEL_, String city_, String road_, double money_){
        name=name_;
        surname=surname_;
        PESEL=PESEL_;
        city=city_;
        road=road_;
        money=money_;
    }
    public Account(String name_,String surname_, String PESEL_, String city_, String road_, String money_){
        name=name_;
        surname=surname_;
        PESEL=Integer.parseInt(PESEL_);
        city=city_;
        road=road_;
        money=Double.parseDouble(money_);
    }

    public String toTextBlock(String... fields){
        StringBuilder ret = new StringBuilder();
        for (String field: fields) {
            switch (field){
                case "name" -> ret.append("Name: ").append(name).append('\n');
                case "surname" -> ret.append("Surname: ").append(surname).append('\n');
                case "PESEL" -> ret.append("PESEL: ").append(PESEL).append('\n');
                case "city" -> ret.append("City: ").append(city).append('\n');
                case "road" -> ret.append("Road: ").append(road).append('\n');
                case "money" -> ret.append("Money: ").append(String.format("%.2f",money)).append("zł").append('\n');
                case "ALL" -> {
                    if(name!=null)ret.append("Name: ").append(name).append('\n');
                    if(surname!=null)ret.append("Surname: ").append(surname).append('\n');
                    if(PESEL>=0)ret.append("PESEL: ").append(PESEL).append('\n');
                    if(city!=null)ret.append("City: ").append(city).append('\n');
                    if(road!=null)ret.append("Road: ").append(road).append('\n');
                    if(money>=0)ret.append("Money: ").append(String.format("%.2f",money)).append("zł").append('\n');
                }
            }
        }
        return ret.toString();
    }

    public void clear(){
        name=null;
        surname=null;
        PESEL=-1;
        city=null;
        road=null;
        money=-1.0;
    }

    public boolean compareTo(Account target){
        boolean ret = true;
        if(name!=null) if (!name.equals(target.getName())) ret = false;
        if(surname!=null) if (!surname.equals(target.getSurname())) ret = false;
        if(PESEL>=0) if (!PESEL.equals(target.getPESEL())) ret = false;
        if(city!=null) if (!city.equals(target.getCity())) ret = false;
        if(road!=null) if (!road.equals(target.getRoad())) ret = false;
        if(money>=0) if (!money.equals(target.getMoney())) ret = false;
        return ret;
    }
}
