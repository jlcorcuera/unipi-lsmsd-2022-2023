package it.unipi.lsmsd.ecommerce.dto.statistics;

import java.util.List;

public class DashboardDTO {
    private float earningsMonthly;
    private float earningsAnnual;
    private int ordersPercentage;
    private Long pendingOrders;

    private List<String> sources;
    private List<Float> totals;

    private double[] earningsByMonth = new double[12];

    public float getEarningsMonthly() {
        return earningsMonthly;
    }

    public void setEarningsMonthly(float earningsMonthly) {
        this.earningsMonthly = earningsMonthly;
    }

    public float getEarningsAnnual() {
        return earningsAnnual;
    }

    public void setEarningsAnnual(float earningsAnnual) {
        this.earningsAnnual = earningsAnnual;
    }

    public int getOrdersPercentage() {
        return ordersPercentage;
    }

    public void setOrdersPercentage(int ordersPercentage) {
        this.ordersPercentage = ordersPercentage;
    }

    public Long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public List<Float> getTotals() {
        return totals;
    }

    public void setTotals(List<Float> totals) {
        this.totals = totals;
    }

    public double[] getEarningsByMonth() {
        return earningsByMonth;
    }

    public void setEarningsByMonth(double[] earningsByMonth) {
        this.earningsByMonth = earningsByMonth;
    }
}
