import { renderWithProviders } from "../../../utils/test-utils";
import { GenresTable } from "./GenreTable";

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
      id: "1",
      name: "test",
      isActive: true,
      deleted_at: null,
      created_at: "2021-09-01T00:00:00.000000Z",
      updated_at: "2021-09-01T00:00:00.000000Z",
      categories: [],
      description: "test",
      pivot: {
        genre_id: "1",
        category_id: "1",
      },
    },
  ],
  per_page: 1,
  total: 1,
  current_page: 1,
};

describe("GenresTable", () => {
  it("should render correctly", () => {
    const { asFragment } = renderWithProviders(<GenresTable {...Props} />);
    expect(asFragment()).toMatchSnapshot();
  });

  it("should render GenresTable with loading", () => {
    const { asFragment } = renderWithProviders(
      <GenresTable {...Props} isFetching={true} />
    );
    expect(asFragment()).toMatchSnapshot();
  });

  it("should render GenresTable with data", () => {
    const { asFragment } = renderWithProviders(
      <GenresTable {...Props} data={mockData} />
    );
    expect(asFragment()).toMatchSnapshot();
  });

  it("should render GenresTable with data and loading", () => {
    const { asFragment } = renderWithProviders(
      <GenresTable {...Props} data={mockData} isFetching={true} />
    );
    expect(asFragment()).toMatchSnapshot();
  });

  it("should render GenresTable with data and loading and delete", () => {
    const { asFragment } = renderWithProviders(
      <GenresTable
        {...Props}
        data={mockData}
        isFetching={true}
        handleDelete={() => {
          console.log("delete");
        }}
      />
    );
    //  expect to find the delete button
    expect(asFragment()).toMatchSnapshot();
  });

  it("should render GenresTable with data categories", () => {
    const { asFragment } = renderWithProviders(
      <GenresTable
        {...Props}
        data={{
          ...mockData,
          itens: [
            {
              ...mockData.itens[0],
              categories: [
                {
                  id: "1",
                  name: "test",
                  description: "test",
                  is_active: true,
                  created_at: "2021-09-01T00:00:00.000000Z",
                  updated_at: "2021-09-01T00:00:00.000000Z",
                  deleted_at: "",
                },
              ],
            },
          ],
        }}
      />
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
