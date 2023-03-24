import { render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { CategoriesTable } from "./CategoryTable";

const Props = {
  data: undefined,
  perPage: 10,
  isFetching: false,
  rowsPerPage: [10, 25, 50],
  handleOnPageChange: () => {},
  handleFilterChange: () => {},
  handleOnPageSizeChange: () => {},
  handleDelete: () => {},
};

const mockData = {
  itens: [
    {
      id: "123",
      name: "test",
      description: "test",
      is_active: true,
      created_at: "2021-03-01T00:00:00.000000Z",
      updated_at: "2021-03-01T00:00:00.000000Z",
      deleted_at: "",
    },
  ],
  current_page: 1,
  per_page: 1,
  total: 1,
};

describe("CategoryTable", () => {
  it("should render correctly", () => {
    const { asFragment } = render(<CategoriesTable {...Props} />);
    expect(asFragment()).toMatchSnapshot();
  });

  it("should render CategoryTable with loading", () => {
    const { asFragment } = render(
      <CategoriesTable {...Props} isFetching={true} />
    );
    expect(asFragment()).toMatchSnapshot();
  });

  it("should render CategoryTable with data", () => {
    const { asFragment } = render(
      <CategoriesTable {...Props} data={mockData} />,
      { wrapper: BrowserRouter }
    );
    expect(asFragment()).toMatchSnapshot();
  });

  it("should render CategoryTable with Inactive value", () => {
    const { asFragment } = render(
      <CategoriesTable
        {...Props}
        data={{
          ...mockData,
          itens: [{ ...mockData.itens[0], is_active: false }],
        }}
      />,
      { wrapper: BrowserRouter }
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
